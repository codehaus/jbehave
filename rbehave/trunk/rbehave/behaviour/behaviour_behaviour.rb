require 'rbehave'

class BehaviourBehaviour < RBehave::Behaviour # no, really!

  def should_succeed_when_Given_When_Then_invoked_in_correct_order
    Given {
      @b = Behaviour.new
    }
    When {
      @b.Given {}
      @b.When {}
      @b.Then {}
    }
    Then {
      ensure_succeeded
    }
  end

  def should_fail_in_When_unless_Given_was_invoked
    Given {
      @b = Behaviour.new
    }
    When {
      @b.When {}
    }
    Then {
      ensure_raised VerificationError
    }
  end

  def should_fail_in_Then_unless_When_was_invoked
    Given {
      @b = Behaviour.new
    }
    When {
      @b.Then {}
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_Given_is_invoked_more_than_once
    Given {
      @b = Behaviour.new
    }
    When {
      @b.Given {}
      @b.Given {}
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_When_is_invoked_more_than_once
    Given {
      @b = Behaviour.new
    }
    When {
      @b.Given {}
      @b.When {}
      @b.When {}
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_Then_is_invoked_more_than_once
    Given {
      @b = Behaviour.new
    }
    When {
      @b.Given {}
      @b.When {}
      @b.Then {}
      @b.Then {}
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_sections_are_called_in_wrong_order
    Given {
      @b = Behaviour.new
    }
    
    When {
      @b.Given {}; @b.Then {}; @b.When {}
    }

    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_reraise_rescued_exception_if_method_should_have_succeeded
    Given {
      @b = Behaviour.new
      @exception = StandardError.new
      
      # something goes wrong in b
      @b.Given{}
      @b.When {
        raise @exception # is this meta enough for you?!
      }
      @b.Then{}
    }
    When {
      @b.ensure_succeeded
    }
    Then {
      ensure_raised @exception
    }
  end
end