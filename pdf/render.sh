#!/usr/bin/env ruby


require 'rubygems'
require 'github/markup'

outfile = "#{ARGV[0]}.html"
puts outfile

#GitHub::Markup.render(outfile, File.read(ARGV[0]))
#
#Markdown.new(File.read(ARGV[0])).to_html

markup(:markdown, /md|mkdn?|markdown/) do |content|
  Markdown.new(content).to_html
end
