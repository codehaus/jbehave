require 'rbehave'
require 'rbehave/listeners'

class RunnerBehaviour < RBehave::Behaviour
  include RBehave
  
  class SheepBehaviour < Behaviour
    include RBehave::DoNotAutorun # otherwise it gets run automagically
    def should_baa
      _given{}; _when{}; _then{}
    end
  end
  
  def should_tell_listener_when_starting_run
    _given {
      @listener = mock(Listener)
      @listener.expects.starting_run
    }
    _when {
      Runner.new(@listener)
    }
    _then {
      verify_mocks
    }
  end
end