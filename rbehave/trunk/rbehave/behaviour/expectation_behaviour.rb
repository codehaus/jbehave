require 'rbehave'

=begin
I want to use a mock like:

  mockFoo = mock(Foo)
=end

class ExpectationBehaviour < RBehave::Behaviour
  include RBehave
  
  # mockFoo.expects.upcase("word").never
  def should_fail_when_called_if_expecting_never_to_be_called
    Given {
      @expectation = Expectation.new(String, :upcase)
      @expectation.never
    }
    When {
      @expectation.call("hello")
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  # mockFoo.expects.upcase("word").never
  def should_verify_ok_if_expecting_never_and_was_never_called
    Given {
      @expectation = Expectation.new(String, :upcase)
      @expectation.never
    }
    When {
      @expectation.verify
    }
    Then {
      ensure_succeeded
    }
  end
  
  # mockFoo.expects.upcase("word")
  def should_match_correct_method_name_with_no_args
    Given {
      @expectation = Expectation.new(String, :upcase)
    }
    When {
      @result = @expectation.matches(:upcase1)
    }
    Then {
      ensure_that @result, is(true)
    }
  end
  
  # mockFoo.expects.upcase("word")
  def should_match_correct_method_name_with_args
    Given {
      @expectation = Expectation.new(String, :upcase).with("hello")
    }
    When {
      @result = @expectation.matches(:upcase, "hello")
    }
    Then {
      ensure_that @result, is(true)
    }
  end
  
  def should_not_match_correct_method_name_with_incorrect_args
    Given {
      @expectation = Expectation.new(String, :upcase).with("hello")
    }
    When {
      @result = @expectation.matches(:upcase, "goodbye")
    }
    Then {
      ensure_that @result, is(false)
    }
  end
  
  def should_match_correct_method_name_with_args_when_none_explicitly_specified
    Given {
      @expectation = Expectation.new(String, :upcase)
    }
    When {
      @result = @expectation.matches(:upcase, "hello")
    }
    Then {
      ensure_that @result, is(true)
    }
  end

  def should_not_match_correct_method_name_with_args_when_no_args_explicitly_set
    Given {
      @expectation = Expectation.new(String, :upcase).with_no_arguments
    }
    When {
      @result = @expectation.matches(:upcase, "hello")
    }
    Then {
      ensure_that @result, is(false)
    }
  end
  
  # mockFoo.expects.upcase("word")
  def should_not_match_different_method_name
    Given {
      @expectation = Expectation.new(String, :upcase)
    }
    When {
      @result = @expectation.matches(:different)
    }
    Then {
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
    Given {
      @expectation = Expectation.new(String, :upcase).with("hello").will_return("HELLO")
    }
    When {
      @result = @expectation.call("hello")
    }
    Then {
      ensure_that @result, is("HELLO")
    }
  end

  def should_fail_if_called_more_times_than_expected
    Given {
      @expectation = Expectation.new(String, :upcase).with("hello").once()
      @expectation.call("hello")
    }
    When {
      @expectation.call("hello")
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_called_fewer_times_than_expected
    Given {
      @expectation = Expectation.new(String, :upcase).with("hello").once()
    }
    When {
      @expectation.verify
    }
    Then {
      ensure_raised VerificationError
    }
  end

  def should_allow_number_of_calls_within_expected_range
    Given {
      @expectation = Expectation.new(String, :upcase).with("hello").times(1..2)
    }
    When {
      @expectation.call("hello")  # 1
      @expectation.call("hello")  # 2
    }
    Then {
      ensure_succeeded
    }
  end
  
  def should_fail_if_fewer_calls_than_within_expected_range
    Given {
      @expectation = Expectation.new(String, :upcase).with("hello").times(1..2)
    }
   When {
      @expectation.verify
    }
    Then {
      ensure_raised VerificationError
    }
  end
  
  def should_fail_if_more_calls_than_within_expected_range
    Given {
      @expectation = Expectation.new(String, :upcase).with("hello").times(1..2)
      @expectation.call("hello")  # 1
      @expectation.call("hello")  # 2
    }
    When {
      @expectation.call("hello")  # 3
    }
    Then {
      ensure_raised VerificationError
    }
  end
end
