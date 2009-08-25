require 'rbehave'

class Sheep
  def baa(); end
  def chew_grass(arg); end
end

class MockBehaviour < RBehave::Behaviour
  include RBehave
  
  def should_fail_for_unexpected_method_call
    _given {
      @sheep = mock(Sheep)
    }
    _when {
      @sheep.baa
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  def should_accept_call_with_no_arguments_when_arguments_unspecified
    _given {
      @sheep = mock(Sheep)
      @sheep.expects.baa
    }
    _when {
      @sheep.baa
    }
    _then {
      ensure_succeeded
    }
  end
  
  def should_accept_call_with_one_argument_when_arguments_unspecified
    _given {
      @sheep = mock(Sheep)
      @sheep.expects.chew_grass
    }
    _when {
      @sheep.chew_grass("A")
    }
    _then {
      ensure_succeeded
    }
  end
  
  def should_accept_call_with_one_argument_when_argument_is_specified
    _given {
      @sheep = mock(Sheep)
      @sheep.expects.chew_grass("A")
    }
    _when {
      @sheep.chew_grass("A")
    }
    _then {
      ensure_succeeded
    }
  end
  
  def should_reject_call_with_incorrect_argument_when_argument_is_specified
    _given {
      @sheep = mock(Sheep)
      @sheep.expects.chew_grass("A")
    }
    _when {
      @sheep.chew_grass("B")
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_on_verify_if_expected_method_was_not_called
    _given {
      @sheep = mock(Sheep)
      @sheep.expects.baa
    }
    _when {
      verify_mocks
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  def should_accept_calls_for_multiple_expectations
    _given {
      @sheep = mock(Sheep)
      @sheep.expects.baa("A")
      @sheep.expects.chew_grass(_not("A"))
    }
    _when {
      @sheep.baa("A")
      @sheep.chew_grass("B")
    }
    _then {
      ensure_succeeded
    }
  end
  
  def should_verify_multiple_expectations
    _given {
      @sheep = mock(Sheep)
      @sheep.expects.baa("A")
      @sheep.expects.chew_grass(_not("A"))
      @sheep.baa("A")
      @sheep.chew_grass("B")
    }
    _when {
      verify_mocks
    }
    _then {
      ensure_succeeded
    }
  end
  
  def should_not_allow_expectation_for_unknown_method
    _given {
      @sheep = mock(Sheep)
    }
    _when {
      @sheep.expects.no_such_method
    }
    _then {
      ensure_raised VerificationError
    }
  end
end
