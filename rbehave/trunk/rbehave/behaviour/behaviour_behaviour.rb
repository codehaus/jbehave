require 'rbehave'

class BehaviourBehaviour < RBehave::Behaviour # no, really
  include RBehave
  
  def should_succeed_when_Given_When_Then_invoked_in_correct_order
    _given {
      @b = Behaviour.new
    }
    _when {
      @b._given {}
      @b._when {}
      @b._then {}
    }
    _then {
      ensure_succeeded
    }
  end

  def should_fail_in_When_unless_Given_was_invoked
    _given {
      @b = Behaviour.new
    }
    _when {
      @b._when {}
    }
    _then {
      ensure_raised VerificationError
    }
  end

  def should_fail_in_Then_unless_When_was_invoked
    _given {
      @b = Behaviour.new
    }
    _when {
      @b._then {}
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_Given_is_invoked_more_than_once
    _given {
      @b = Behaviour.new
      @b._given {}
    }
    _when {
      @b._given {}
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_When_is_invoked_more_than_once
    _given {
      @b = Behaviour.new
      @b._given {}
      @b._when {}
    }
    _when {
      @b._when {}
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_Then_is_invoked_more_than_once
    _given {
      @b = Behaviour.new
      @b._given {}; @b._when {}; @b._then {}
    }
    _when {
      @b._then {}
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_sections_are_called_in_wrong_order
    _given {
      @b = Behaviour.new
    }
    
    _when {
      @b._given {}; @b._then {}; @b._when {}
    }

    _then {
      ensure_raised VerificationError
    }
  end
  
  class WhenFailed < StandardError; end
  class ThenFailed < StandardError; end
  
  def should_report_exception_from_Then_block_if_When_and_Then_both_fail
    _given {
      @b = Behaviour.new
      @b._given{}
      @b._when { raise WhenFailed }
    }
    _when {
      @b._then { raise ThenFailed }
    }
    _then {
      ensure_raised ThenFailed
    }
  end
  
  def should_reraise_exception_in_Then_block_if_When_failed_with_an_unexpected_error
    _given {
      @b = Behaviour.new
      @b._given{}
      @b._when { raise WhenFailed }
    }
    _when {
      @b._then {} # doesn't check for WhenFailed
    }
    _then {
      ensure_raised WhenFailed
    }
  end
end