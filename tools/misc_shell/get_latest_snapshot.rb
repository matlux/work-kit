#!/usr/bin/env ruby

#---------------------------------------------------------------------
#
#  Parse the response from Sonatype Nexus in order to determine the
#  correct URI for the most recent snapshot of an artifact.
#
#  Usage:
#      ruby get_latest_snapshot.rb \
#        -n http://localhost:8080/nexus \
#        -g uk.co.scattercode \
#        -a  my-artifact \
#        -v 1.0.0 \
#        -c jar-with-dependencies \
#        -p jar
#
#  Raison d'etre: Sonatype Nexus provides an API for searching for artifacts.
#  Maven snapshot builds are generated with a time-stamp on them. The API
#  returns the name of the artifact without the time-stamp. I think that this
#  is intended to be a 'good thing' with Nexus automatically resolving the
#  latest snapshot based on requesting the snapshot with no time-stamp.
#  Unfortunately, requesting that artifact from the location indicated by the
#  API results in a 'not found' response. Therefore this script is intended
#  to go to the URI at which the full artifact list can be found, which
#  includes resources such as poms, jars, sha1 and md5 hashes. It narrows
#  down the results and selects the most recent artifact that matches the
#  search criteria.
#
#  Find the latest version of this script here:
#    https://gist.github.com/1852106
#
#---------------------------------------------------------------------

require 'getoptlong'
require 'net/http'
require 'rexml/document'
require 'open-uri'

#
# Let folks know what args they could use.
#
def show_help
  puts <<-EOF
Usage:
  ruby get_latest_snapshot.rb [OPTION] ...

  -h, --help:
     show help

  --file [file], -f [file]:
     File to get API response from instead of URL.

  --nexus [host], -n [host]:
     The base URL of the Nexus server.

  --artifact, -a:
    The name of the artifact.

  --version, -v:
    The version of the artifact (1.0.0, 1.0.0-SNAPSHOT, ...).

  --classifier, -c:
    The classifier, which gets appended to the name. As defined by 'descriptorRef' in Maven assembly plugin.

  --package, -p:
    The package type (jar, war, ear, ...).
   EOF
end


#
# Download the artifact.
#
def download(uri, filename)
  puts "Downloading \n  from uri: #{uri} \n  to file: #{filename}"

  open(filename, 'wb') do |fo|
    fo.print open(uri).read
  end

  puts "I think I just downloaded: #{filename}"
end


#
# Determine the appropriate filename.
#
def filename(artifact, version, classifier, package)

  if /-SNAPSHOT/.match(version)
    vnum = /.+(?=-SNAPSHOT)/.match(version).to_s
  else
    vnum = version
  end

  if classifier == nil
    filename = "#{artifact}-#{vnum}.#{package}"
  else
    filename = "#{artifact}-#{vnum}-#{classifier}.#{package}"
  end
  return filename
end


opts = GetoptLong.new(
  ['--help', '-h', GetoptLong::NO_ARGUMENT],
  ['--file', '-f', GetoptLong::OPTIONAL_ARGUMENT],
  ['--nexus', '-n', GetoptLong::OPTIONAL_ARGUMENT],
  ['--group', '-g', GetoptLong::REQUIRED_ARGUMENT],
  ['--artifact', '-a', GetoptLong::REQUIRED_ARGUMENT],
  ['--version', '-v', GetoptLong::REQUIRED_ARGUMENT],
  ['--classifier', '-c', GetoptLong::OPTIONAL_ARGUMENT],
  ['--package', '-p', GetoptLong::REQUIRED_ARGUMENT]
)

file = nil
nexus = nil
group = nil
artifact = nil
version = nil
classifier = nil
package = nil


opts.each do |opt, arg|
  case opt

    when '--help'
      show_help
    when '--file'
      file = arg
    when '--nexus'
      nexus = arg
    when '--group'
      group = arg
    when '--artifact'
      artifact = arg
    when '--version'
      version = arg
    when '--classifier'
      classifier = arg
    when '--package'
      package = arg

  end
end

puts <<-EOF
  Args as follows:
    file = #{file}
    nexus = #{nexus}
    group = #{group}
    artifact = #{artifact}
    version = #{version}
    classifier = #{classifier}
    package = #{package}
EOF

#
# Now we get to the meat of the script.
#
# I'm going to ignore the search API and just query the 'directory'
# in which Nexus should be holding the artifacts.

if file != nil

  # We have been given a file with the directory contents XML.
  # Most likely for test purposes...
  puts "Getting directory XML from file: #{file}"
  xml = File.open(file)

else

  url="#{nexus}/service/local/repositories/public-snapshots/content/#{group}/#{artifact}/#{version}/"
  puts "Gettting directory XML from URL: #{url}"
  xml = Net::HTTP.get_response( URI.parse( url ) ).body

  puts xml
end

doc = REXML::Document.new(xml)


most_recent_uri = nil
most_recent_snapshot_id = nil

doc.elements.each("//resourceURI") {|r|

  uri = r.text

  # puts "Looking at uri: #{uri}"

  # Filter out hashes and irrelevant artifacts.
  if classifier == nil
    match = /\d+.#{package}$/
  else
    match = /#{classifier}.#{package}$/
  end

  if uri =~ match
    if classifier == nil
      seq = /(?<=-)\d+(?=.#{package}$)/.match(uri).to_s.to_i
    else
      seq = /(?<=-)\d+(?=-#{classifier}.#{package}$)/.match(uri).to_s.to_i
    end
    puts "Found matching uri with sequence ID: #{seq}"

    if most_recent_uri == nil || seq > most_recent_snapshot_id
      most_recent_uri = uri
      most_recent_snapshot_id = seq
    end

  end
}

if most_recent_uri == nil
  puts "Unable to find an artifact matching those criteria."
else
  puts "The most recent snapshot of that artifact is here: \n    #{most_recent_uri}"
end

download(most_recent_uri, filename(artifact, version, classifier, package))
