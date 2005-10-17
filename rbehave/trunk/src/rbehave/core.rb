require 'rbehave/constraints'
require 'rbehave/verifiers'
require 'rbehave/runner'
require 'rbehave/util'

module RBehave
  include Constraints
  
  class Behaviour
  end
  
  at_exit {
    runner = Runner.new
    behaviour_classes = []
    
    if (ARGV.size > 0)
      ARGV.each { |cls| behaviour_classes << Class.for_name(cls) }
    else
      ObjectSpace.each_object(Behaviour.class) { |cls| behaviour_classes << cls }
    end
    
    behaviour_classes.each { |cls| runner.verify cls }
    
    runner.summarize
  }
end
