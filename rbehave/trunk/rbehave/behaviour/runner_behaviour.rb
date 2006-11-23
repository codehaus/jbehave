require 'rbehave'
require 'rbehave/listeners'

class RunnerBehaviour < RBehave::Behaviour
  include RBehave
  
  class SheepBehaviour < Behaviour
    include RBehave::DoNotAutorun # otherwise it gets run automagically
    def should_baa
      Given{}; When{}; Then{}
    end
  end
  
  def should_tell_listener_when_starting_run
    Given {
      @listener = mock(Listener)
      @listener.expects.starting_run
    }
    When {
      Runner.new(@listener)
    }
    Then {
      verify_mocks
    }
  end
end