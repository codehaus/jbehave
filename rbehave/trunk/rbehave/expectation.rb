require 'rbehave/constraint'

module RBehave
  module Mocks
    class Expectation
      include Constraints
      
      def initialize(type, method)
        @type = type
        @method = method
        @min_calls = @max_calls = 1
        @calls = 0
        @is_stub = false
      end
            
      def matches(method, *args)
        return false if @method != method
        return true if @constraints == nil # don't care about args
        return false if @constraints.length != args.length
        @constraints.each_index do |i|
          return false unless @constraints[i].matches(args[i])
        end
        return true
      end
      
      def times(n)
        case n
          when Fixnum then @min_calls = @max_calls = n
          when Range  then @min_calls, @max_calls = n.min, n.max
        end
        self
      end
      
      def never; times(0); end
      
      def once; times(1); end
      
      def stubs
        @is_stub = true
      end
      
      def at_least(n)
        @min_calls = n
        self
      end
      
      def at_most(n)
        @max_calls = n
        self
      end
      
      def call(*args)
        if @calls >= @max_calls and not @is_stub
          raise VerificationError, "#{@method} already called #{@calls} times"
        end
        @calls += 1
        return @return_value
      end
      
      def with(*args)
        @constraints = args.map { |arg| arg.is_a?(Constraint) ? arg : eq(arg) }
        self
      end
      
      def with_no_arguments
        @constraints = []
        self
      end
      
      def will_return(value)
        @return_value = value
        self
      end
      
      def verify
        if @calls < @min_calls and not @is_stub
          raise VerificationError, "#{self}: #{@type}.#{@method}: #{@calls} calls made. Expected at least #{@min_calls} calls"
        end
      end
    end
  end
end
