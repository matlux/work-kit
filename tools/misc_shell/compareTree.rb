#!/usr/bin/env ruby

def filenames(dir) # return basenames of all files in tree
    Dir["#{dir}/**/*"].map{|f| f.gsub(/^.*\//,'')}.sort
end

dir1, dir2 = ARGV[0..1]

puts (filenames(dir1) & filenames(dir2)).join("\n")
