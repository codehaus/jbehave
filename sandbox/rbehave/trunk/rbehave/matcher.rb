require 'rbehave/exceptions'

module RBehave
  module Matchers

    # superclass for all matcher types
    class Matcher
      def matches(arg); raise VerificationError, "matches(arg) must be overridden"; end
      def to_s; raise VerificationError, "to_s() must be overridden"; end
    end
    
    class CustomMatcher < Matcher
      def initialize(description, &matches)
        @description, @matches = description, matches
      end
      
      def matches(arg)
  #      puts "Checking match for #{@description}..."
        return @matches.call(arg)
      end
      
      def to_s
        return @description
      end
    end
    
    #
    # Utility method for creating matchers
    #
    # Use like:
    #
    # <pre><code>
    # def my_matcher(arg)
    #   return matcher("description including #{arg}") do
    #     # body of matcher goes here
    #   end
    # end
    def matcher(description, &matches)
      return CustomMatcher.new(description, &matches)
    end
    
    #========================= The Matchers ============================
    
    def eq(expected)
      return matcher("equal to <#{expected.to_s}>") do |arg|
        expected == arg
      end
    end
    
    alias :is :eq
    
    def anything
      return matcher("anything") do |arg|
        true
      end
    end
      
    def is_true
      return matcher("true") do |arg|
        arg
      end
    end
    
    def is_false
      return matcher("false") do |arg|
        not arg
      end
    end
    
    # string matchers
  
    def contains(fragment)
      return matcher("string containing <#{fragment}>") do |arg|
        if arg < Enumerable
          arg.member? fragment
        else
         arg.to_s =~ /#{fragment}/
        end
      end
    end
    
    def starts_with(fragment)
      return matcher("string starting with <#{fragment}>") do
        |arg| arg.to_s =~ /^#{fragment}/
      end
    end
    
    def ends_with(fragment)
      return matcher("string ending with <#{fragment}>") do |arg|
        arg.to_s =~ /#{fragment}$/
      end
    end
    
    # logical operators
  
    def to_matcher(c)
      c.is_a?(Matcher) ? c : eq(c)
    end
    
    def _not(c)
      c = to_matcher(c)
      return matcher("not #{matcher.to_s}") do |arg|
        not matcher.matches(arg)
      end
    end
    
    alias :is_not :_not
    
    def _and(c1, c2)
      c1, c2 = to_matcher(c1), to_matcher(c2)
      return matcher("( #{c1.to_s} and #{c2.to_s} )") do |arg|
        c1.matches(arg) && c2.matches(arg)
      end
    end
    
    alias :both :_and
    alias :_both :_and
    
    def _or(c1, c2)
      c1, c2 = to_matcher(c1), to_matcher(c2)
      return matcher("( #{c1.to_s} or #{c2.to_s} )") do |arg|
        c1.matches(arg) || c2.matches(arg)
      end
    end
    
    alias :either :_or
    alias :_either :_or
    
    def _not(c)
      c = to_matcher(c)
      return matcher("not( #{c.to_s} )") do |arg|
        not c.matches(arg)
      end
    end
    
    # add methods to Matcher class
    class Matcher
      def _and(matcher); both(self, matcher); end
      def _or(matcher);  either(self, matcher); end
    end
  
    # ensure_that
    
    def ensure_that arg, matcher = is_true, message = nil
      unless matcher.matches arg
        raise VerificationError, "Expected: " + (message ? message : "") + "[" + matcher.to_s + "]" +
          "\nbut got:  [" + arg.to_s + "]" #, clean_backtrace(caller)
      end
    end
    
    def ensure_raises(expected_error, &block)
      begin
        yield
      rescue expected_error
        return
      end
      raise VerificationError, "Should have failed with #{expected_error}" #, clean_backtrace(caller)
    end
    
    def fail_with(message)
      raise VerificationError, message #, clean_backtrace(caller)
    end
  end
end
