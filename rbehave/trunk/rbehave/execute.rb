require 'rbehave/runner'
require 'rbehave/listeners'

module RBehave
  at_exit do
    runner = Runner.new(TextListener.new)
    behaviour_classes = []
    
    ObjectSpace.each_object(Class.class) do |cls|
      if cls < Behaviour
        behaviour_classes << cls unless cls < DoNotAutorun
      end
    end
    
    behaviour_classes.each { |cls| runner.verify(cls) }
    
    runner.summarize
  end
end
