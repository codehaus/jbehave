require 'rbehave'

class Sheep
  def baa(); end
  def chew_grass(arg); end
end

class MockBehaviour < RBehave::Behaviour
  include RBehave
  
  def should_fail_for_unexpected_method_call
    Given {
      @sheep = mock(Sheep)
    }
    When {
      @sheep.baa
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_accept_call_with_no_arguments_when_arguments_unspecified
    Given {
      @sheep = mock(Sheep)
      @sheep.expects.baa
    }
    When {
      @sheep.baa
    }
    Then {
      ensure_succeeded
    }
  end
  
  def should_accept_call_with_one_argument_when_arguments_unspecified
    Given {
      @sheep = mock(Sheep)
      @sheep.expects.chew_grass
    }
    When {
      @sheep.chew_grass("A")
    }
    Then {
      ensure_succeeded
    }
  end
  
  def should_accept_call_with_one_argument_when_argument_is_specified
    Given {
      @sheep = mock(Sheep)
      @sheep.expects.chew_grass("A")
    }
    When {
      @sheep.chew_grass("A")
    }
    Then {
      ensure_succeeded
    }
  end
  
  def should_reject_call_with_incorrect_argument_when_argument_is_specified
    Given {
      @sheep = mock(Sheep)
      @sheep.expects.chew_grass("A")
    }
    When {
      @sheep.chew_grass("B")
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_on_verify_if_expected_method_was_not_called
    Given {
      @sheep = mock(Sheep)
      @sheep.expects.baa
    }
    When {
      verify_mocks
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_accept_calls_for_multiple_expectations
    Given {
      @sheep = mock(Sheep)
      @sheep.expects.baa("A")
      @sheep.expects.chew_grass(_not("A"))
    }
    When {
      @sheep.baa("A")
      @sheep.chew_grass("B")
    }
    Then {
      ensure_succeeded
    }
  end
  
  def should_verify_multiple_expectations
    Given {
      @sheep = mock(Sheep)
      @sheep.expects.baa("A")
      @sheep.expects.chew_grass(_not("A"))
      @sheep.baa("A")
      @sheep.chew_grass("B")
    }
    When {
      verify_mocks
    }
    Then {
      ensure_succeeded
    }
  end
  
  def should_not_allow_expectation_for_unknown_method
    Given {
      @sheep = mock(Sheep)
    }
    When {
      @sheep.expects.no_such_method
    }
    Then {
      ensure_raised VerificationError
    }
  end
end
