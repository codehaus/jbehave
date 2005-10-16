require 'rbehave/constraints'
require 'rbehave/runner'
require 'rbehave/util'

module RBehave
  include Constraints

  class UsingConstraints
    def ensure_that arg, constraint, message = nil
      unless constraint.matches arg
        raise VerificationError, "Expected:" + (message ? "[" + message + "] " : "") + constraint.to_s + "\nbut got:  " + arg.to_s
      end
    end
  end
  
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
