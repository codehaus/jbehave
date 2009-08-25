#!/usr/bin/env ruby

require 'rbehave/behaviour'

if ARGV[0] == 'describe'
  require 'rbehave/describe'
else
  require 'rbehave/execute'
end
