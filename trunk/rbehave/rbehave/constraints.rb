module RBehave
  class VerificationError < StandardError; end
  
  module Constraints
    # superclass for all constraint types
    class Constraint
      def matches arg; raise VerificationError, "matches(arg) must be overridden"; end
      def to_s; raise VerificationError, "to_s() must be overridden"; end
    end
    
    class Contains < Constraint
      def initialize fragment
        @fragment = fragment
      end
      def matches arg
        return arg.to_s =~ /#{@fragment}/s
      end
      def to_s
        "string containing <#{@fragment}>"
      end
    end
    
    def contains fragment
      return Contains.new(fragment)
    end
  end
end