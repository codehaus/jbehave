module RBehave
  module Verifiers
  
    class Verifier
      @@runners = []
      
      def Verifier.register_runner(runner)
        @@runners << runner
      end
    
      def initialize(type)
        @type = type
        @@runners.each{ |r| r.register_verifier self}
      end
      
      class Echoer
        def method_missing(method, *args)
          puts method
        end
      end
      
      def __should_receive(&messages)
        messages.call(Echoer.new)
      end
      
      def __verify
        puts "verifying for type " + @type.class.to_s
      end
    end
    
    def verifier_for(type)
      return Verifier.new(type)
    end
  end
end