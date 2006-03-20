require 'rbehave'
require 'rbehave/expectation'

=begin
I want to use a mock like:

  mockFoo = mock(Foo)
=end

class ExpectationBehaviour < RBehave::Behaviour
  include Mocks
  
  # mockFoo.expects.upcase("word").never
  def should_fail_when_called_if_expecting_never_to_be_called
   expectation = Expectation.new(:upcase)
   expectation.never
   ensure_raises VerificationError do
    expectation.call("hello")
   end
  end
  
  # mockFoo.expects.upcase("word").never
  def should_verify_ok_if_expecting_never_and_was_never_called
    expectation = Expectation.new(:upcase)
    expectation.never
    expectation.verify
  end
  
  # mockFoo.expects.upcase("word")
  def should_match_correct_method_name_with_no_args
    expectation = Expectation.new(:upcase)
    ensure_that expectation.matches(:upcase)
  end
  
  # mockFoo.expects.upcase("word")
  def should_match_correct_method_name_with_args
    expectation = Expectation.new(:upcase).with("hello")
    ensure_that expectation.matches(:upcase, "hello")
  end
  
  def should_not_match_correct_method_name_with_incorrect_args
    expectation = Expectation.new(:upcase).with("hello")
    ensure_that expectation.matches(:upcase, "goodbye"), is_false
  end
  
  def should_match_correct_method_name_with_args_when_none_explicitly_specified
    expectation = Expectation.new(:upcase)
    ensure_that expectation.matches(:upcase, "hello")
  end

  def should_not_match_correct_method_name_with_args_when_no_args_explicitly_set
    expectation = Expectation.new(:upcase).with_no_arguments
    ensure_that expectation.matches(:upcase, "hello"), is_false
  end
  
  # mockFoo.expects.upcase("word")
  def should_not_match_different_method_name
    expectation = Expectation.new(:upcase)
    ensure_that expectation.matches(:different), is_false
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
    expectation = Expectation.new(:upcase).with("hello").will_return("HELLO")
    result = expectation.call("hello")
    ensure_that result, eq("HELLO")
  end

  def should_raise_verification_error_if_called_more_times_than_expected
    expectation = Expectation.new(:upcase).with("hello").once()
    expectation.call("hello")
    ensure_raises VerificationError do
      expectation.call("hello")
    end
  end
  
  def should_raise_verification_error_if_called_fewer_times_than_expected
    expectation = Expectation.new(:upcase).with("hello").once()
    ensure_raises VerificationError do
      expectation.verify
    end
  end
  
  def should_allow_number_of_calls_within_expected_range
    expectation = Expectation.new(:upcase).with("hello").times(1..3)
    
    ensure_raises VerificationError do
      expectation.verify # not called yet
    end
    expectation.call("hello")  # 1
    expectation.verify
    expectation.call("hello")  # 2
    expectation.verify
    expectation.call("hello")  # 3
    expectation.verify
    
    ensure_raises VerificationError do
      expectation.call("hello") # too many times
    end
  end
end
