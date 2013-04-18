# Standard libraries
require "pathname"
require "fileutils"
require "yaml"

# Gem libraries
require "archive/tar/minitar"
require "rye"

# Related libraries
require "parsegpu/errors"

# includes
include FileUtils

class ParseGpu

  def self.cli(args)
    app = ParseGpu.new

    OptionParser.new do |parser|
      parser.banner = <<-HERE
USAGE: parseGpu [options]

EXAMPLES:
  # TODO

OPTIONS:
      HERE

      options = {}

      options[:deploy] = true

      parser.on("-k", "--kill HOSTNAME", "kill HOSTNAME or all active machines if \"all\" is passed") do |name|
        options[:deploy] = false
        options[:kill] = true
      end



      begin
        arguments = parser.parse!
        puts "arguments=#{arguments} options=#{options}"
      rescue OptionParser::MissingArgument => e
        puts parser
        puts
        puts "ERROR: #{e}"
        exit -1
      rescue OptionParser::InvalidArgument => e
        puts parser
        puts
        puts "ERROR: #{e}"
        exit -1
      end

      begin
      	puts "options=#{options}"
        t1 = Time.now
        
        app.executeAction()
      #rescue NodeError => e
      #  puts "! #{e.node}: #{e}"
      #  exit -1
      end
      t2 = Time.now
      delta = t2 - t1
      puts "elasped time: #{delta}s"
    end
  end

  # Amount of detail to display? true means verbose, nil means normal, false means quiet.
  attr_accessor :verbosity




  def initialize(opts={})
    puts "opts=#{opts}"
    self.verbosity   = opts[:verbosity]

  end



  def executeAction()
    puts "Hello world"

  end









end
