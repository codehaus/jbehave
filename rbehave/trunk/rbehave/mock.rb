require 'rbehave/expectation'
require 'rbehave/core'

module RBehave
  module Mocks
    def Mocks.register_expectations(expectations)
      @@all_expectations ||= [] # list of lists of expectations
      @@all_expectations << expectations
    end
    
    def Mocks.clear
      @@all_expectations = []
    end
    
    def verify_mocks
      @@all_expectations.each do |expectations|
        expectations.each { |ex| ex.verify }
      end
      @@all_expectations.clear
    end
    
    def mock(type)
      Mock.new(type)
    end
    
    class Mock
      include Utils
      
      def initialize(type)
        @type = type
        @stubbing = @expecting = false
        @expectations = []
        Mocks.register_expectations(@expectations)
      end
      
      def stubs
        @stubbing = true
        @expecting = true
        self
      end
      
      def expects
        @expecting = true
        self
      end
      
      attr_reader :expectations
      
      def method_missing(meth, *args)
        if @expecting
          raise VerificationError, "Unknown method: #{@type}.#{meth}" unless @type.public_method_defined?(meth)
          ex = Expectation.new(@type, meth)
          ex.with(*args) unless args.empty?
          ex.stubs if @stubbing
          @expectations << ex
          @expecting = @stubbing = false
          return ex
        end
        
        @expectations.each do |ex|
          return ex.call(meth, *args) if ex.matches(meth, *args)
        end
        raise VerificationError, "Unexpected call: #{meth}(#{args.join(", ")})", clean_backtrace
      end
    end
  end
end
