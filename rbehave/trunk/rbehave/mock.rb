require 'rbehave/expectation'
require 'rbehave/core'

module RBehave
  module Mocks
    def Mocks.register_expectations(expectations)
      @@all_expectations ||= [] # list of lists of expectations
      @@all_expectations << expectations
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
        @expecting = false
        @expectations = []
        Mocks.register_expectations(@expectations)
      end
      
      def expects
        @expecting = true
        self
      end
      
      def method_missing(meth, *args)
        if @expecting
          raise VerificationError, "Unknown method: #{@type}.#{meth}" unless @type.public_method_defined?(meth)
          ex = Expectation.new(meth)
          ex.with(*args) unless args.empty?
          @expectations << ex
          @expecting = false
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
