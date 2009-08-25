require 'rbehave'

=begin
I want to use a mock like:

  mockFoo = mock(Foo)
=end

class ExpectationBehaviour < RBehave::Behaviour
  include RBehave
  
  # mockFoo.expects.upcase("word").never
  def should_fail_when_called_if_expecting_never_to_be_called
    _given {
      @expectation = Expectation.new(String, :upcase)
      @expectation.never
    }
    _when {
      @expectation.call("hello")
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  # mockFoo.expects.upcase("word").never
  def should_verify_ok_if_expecting_never_and_was_never_called
    _given {
      @expectation = Expectation.new(String, :upcase)
      @expectation.never
    }
    _when {
      @expectation.verify
    }
    _then {
      ensure_succeeded
    }
  end
  
  # mockFoo.expects.upcase("word")
  def should_match_correct_method_name_with_no_args
    _given {
      @expectation = Expectation.new(String, :upcase)
    }
    _when {
      @result = @expectation.matches(:upcase)
    }
    _then {
      ensure_that @result, is(true)
    }
  end
  
  # mockFoo.expects.upcase("word")
  def should_match_correct_method_name_with_args
    _given {
      @expectation = Expectation.new(String, :upcase).with("hello")
    }
    _when {
      @result = @expectation.matches(:upcase, "hello")
    }
    _then {
      ensure_that @result, is(true)
    }
  end
  
  def should_not_match_correct_method_name_with_incorrect_args
    _given {
      @expectation = Expectation.new(String, :upcase).with("hello")
    }
    _when {
      @result = @expectation.matches(:upcase, "goodbye")
    }
    _then {
      ensure_that @result, is(false)
    }
  end
  
  def should_match_correct_method_name_with_args_when_none_explicitly_specified
    _given {
      @expectation = Expectation.new(String, :upcase)
    }
    _when {
      @result = @expectation.matches(:upcase, "hello")
    }
    _then {
      ensure_that @result, is(true)
    }
  end

  def should_not_match_correct_method_name_with_args_when_no_args_explicitly_set
    _given {
      @expectation = Expectation.new(String, :upcase).with_no_arguments
    }
    _when {
      @result = @expectation.matches(:upcase, "hello")
    }
    _then {
      ensure_that @result, is(false)
    }
  end
  
  # mockFoo.expects.upcase("word")
  def should_not_match_different_method_name
    _given {
      @expectation = Expectation.new(String, :upcase)
    }
    _when {
      @result = @expectation.matches(:different)
    }
    _then {
      ensure_that @result, is(false)
    }
  end
  
  # mockFoo.expects.upcase("hello").will_return("HELLO")
  #
  # TODO implement expect taking a block
  # mockFoo.expects do |m|
  #   m.upcase("hello").will_return("HELLO")
  #   m.do_something().will_raise(RuntimeError, "blah")
  # end
  #
  def should_return_preset_value_when_invoked
    _given {
      @expectation = Expectation.new(String, :upcase).with("hello").will_return("HELLO")
    }
    _when {
      @result = @expectation.call("hello")
    }
    _then {
      ensure_that @result, is("HELLO")
    }
  end

  def should_fail_if_called_more_times_than_expected
    _given {
      @expectation = Expectation.new(String, :upcase).with("hello").once()
      @expectation.call("hello")
    }
    _when {
      @expectation.call("hello")
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_called_fewer_times_than_expected
    _given {
      @expectation = Expectation.new(String, :upcase).with("hello").once()
    }
    _when {
      @expectation.verify
    }
    _then {
      ensure_raised VerificationError
    }
  end

  def should_allow_number_of_calls_within_expected_range
    _given {
      @expectation = Expectation.new(String, :upcase).with("hello").times(1..2)
    }
    _when {
      @expectation.call("hello")  # 1
      @expectation.call("hello")  # 2
    }
    _then {
      ensure_succeeded
    }
  end
  
  def should_fail_if_fewer_calls_than_within_expected_range
    _given {
      @expectation = Expectation.new(String, :upcase).with("hello").times(1..2)
    }
   _when {
      @expectation.verify
    }
    _then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_more_calls_than_within_expected_range
    _given {
      @expectation = Expectation.new(String, :upcase).with("hello").times(1..2)
      @expectation.call("hello")  # 1
      @expectation.call("hello")  # 2
    }
    _when {
      @expectation.call("hello")  # 3
    }
    _then {
      ensure_raised VerificationError
    }
  end
end
