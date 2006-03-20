require 'rbehave/core'

module RBehave
  
  module Constraints
    include Utils

    # superclass for all constraint types
    class Constraint
      def matches(arg); raise VerificationError, "matches(arg) must be overridden"; end
      def to_s; raise VerificationError, "to_s() must be overridden"; end
    end
    
    class CustomConstraint < Constraint
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
    # Utility method for creating constraints
    #
    # Use like:
    #
    # <pre><code>
    # def my_constraint(arg)
    #   return constraint("description including #{arg}") do
    #     # body of constraint goes here
    #   end
    # end
    def constraint(description, &matches)
      return CustomConstraint.new(description, &matches)
    end
    
    #========================= The Constraints ============================
    
    def eq(expected)
      return constraint("equal to <#{expected.to_s}>") do |arg|
        expected == arg
      end
    end
    
    def anything
      return constraint("anything") do |arg|
        true
      end
    end
      
    def is_true
      return constraint("true") do |arg|
        arg
      end
    end
    
    def is_false
      return constraint("false") do |arg|
        not arg
      end
    end
    
    # string constraints
  
    def contains(fragment)
      return constraint("string containing <#{fragment}>") do |arg|
        if arg < Enumerable
          arg.member? fragment
        else
         arg.to_s =~ /#{fragment}/
        end
      end
    end
    
    def starts_with(fragment)
      return constraint("string starting with <#{fragment}>") do
        |arg| arg.to_s =~ /^#{fragment}/
      end
    end
    
    def ends_with(fragment)
      return constraint("string ending with <#{fragment}>") do |arg|
        arg.to_s =~ /#{fragment}$/
      end
    end
    
    # logical operators
  
    def to_constraint(c)
      c.is_a?(Constraint) ? c : eq(c)
    end
    
    def _not(c)
      c = to_constraint(c)
      return constraint("not #{constraint.to_s}") do |arg|
        result = constraint.matches(arg)
        puts result
        raise RuntimeError
        not result
      end
    end
    
    alias :is_not :_not
    
    def _and(c1, c2)
      c1, c2 = to_constraint(c1), to_constraint(c2)
      return constraint("( #{c1.to_s} and #{c2.to_s} )") do |arg|
        c1.matches(arg) && c2.matches(arg)
      end
    end
    
    alias :both :_and
    alias :_both :_and
    
    def _or(c1, c2)
      c1, c2 = to_constraint(c1), to_constraint(c2)
      return constraint("( #{c1.to_s} or #{c2.to_s} )") do |arg|
        c1.matches(arg) || c2.matches(arg)
      end
    end
    
    alias :either :_or
    alias :_either :_or
    
    def _not(c)
      c = to_constraint(c)
      return constraint("not( #{c.to_s} )") do |arg|
        not c.matches(arg)
      end
    end
    
    # add methods to Constraint class
    class Constraint
      def _and(constraint); both(self, constraint); end
      def _or(constraint);  either(self, constraint); end
    end
  
    # ensure_that
    
    def ensure_that arg, constraint = is_true, message = nil
      unless constraint.matches arg
        raise VerificationError, "Expected: " + (message ? message : "") + "[" + constraint.to_s + "]" +
          "\nbut got:  [" + arg.to_s + "]", clean_backtrace(caller)
      end
    end
    
    def ensure_raises(expected_error, &block)
      begin
        yield
      rescue expected_error
        return
      end
      raise VerificationError, "Should have failed with #{expected_error}", clean_backtrace(caller)
    end
    
    def fail_with(message)
      raise VerificationError, message, clean_backtrace(caller)
    end
  end
end
