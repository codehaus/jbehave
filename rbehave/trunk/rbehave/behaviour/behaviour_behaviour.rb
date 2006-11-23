require 'rbehave'

class BehaviourBehaviour < RBehave::Behaviour # no, really
  include RBehave
  
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
      @b.Given {}
    }
    When {
      @b.Given {}
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_When_is_invoked_more_than_once
    Given {
      @b = Behaviour.new
      @b.Given {}
      @b.When {}
    }
    When {
      @b.When {}
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_Then_is_invoked_more_than_once
    Given {
      @b = Behaviour.new
      @b.Given {}; @b.When {}; @b.Then {}
    }
    When {
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
  
  class WhenFailed < StandardError; end
  class ThenFailed < StandardError; end
  
  def should_report_exception_from_Then_block_if_When_and_Then_both_fail
    Given {
      @b = Behaviour.new
      @b.Given{}
      @b.When { raise WhenFailed }
    }
    When {
      @b.Then { raise ThenFailed }
    }
    Then {
      ensure_raised ThenFailed
    }
  end
  
  def should_reraise_exception_in_Then_block_if_When_failed_with_an_unexpected_error
    Given {
      @b = Behaviour.new
      @b.Given{}
      @b.When { raise WhenFailed }
    }
    When {
      @b.Then {} # doesn't check for WhenFailed
    }
    Then {
      ensure_raised WhenFailed
    }
  end
end