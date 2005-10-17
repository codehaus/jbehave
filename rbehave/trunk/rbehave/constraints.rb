module RBehave
  class VerificationError < StandardError; end
  
  module Constraints
    # superclass for all constraint types
    class Constraint
      def matches(arg); raise VerificationError, "matches(arg) must be overridden"; end
      def to_s; raise VerificationError, "to_s() must be overridden"; end
    end
    
    class ProcConstraint < Constraint
      def initialize(matches_proc, to_s_proc)
        @matches, @to_s = matches_proc, to_s_proc
      end
      
      def matches(arg); return @matches.call(arg); end
      
      def to_s; return @to_s.call; end
    end
    
    def eq(expected)
      return ProcConstraint.new(
        Proc.new {|arg| expected == arg },
        Proc.new {"equal to <#{expected}>" }
      )
    end
    
    # string constraints

    def contains(fragment)
      return ProcConstraint.new(
        Proc.new {|arg| arg.to_s =~ /#{fragment}/ },
        Proc.new { "string containing <#{fragment}>" }
      )
    end
    
    def starts_with(fragment)
      return ProcConstraint.new(
        Proc.new {|arg| arg.to_s =~ /^#{fragment}/ },
        Proc.new { "string starting with <#{fragment}>" }
      )
    end
    
    def ends_with(fragment)
      return ProcConstraint.new(
        Proc.new {|arg| arg.to_s =~ /#{fragment}$/ },
        Proc.new { "string ending with <#{fragment}>" }
      )
    end
  end
end