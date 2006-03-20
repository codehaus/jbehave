require 'rbehave/core'
require 'rbehave/mock'

class MockBehaviour < RBehave::Behaviour
  include Mocks
  
  class Foo
    def do_something(); end
    def do_another_thing(arg); end
  end
  
  def should_fail_for_unexpected_method_call
    foo = mock(Foo)
    ensure_raises VerificationError do
      foo.do_something
    end
  end
  
  def should_accept_call_with_no_arguments_when_arguments_unspecified
    foo = mock(Foo)
    foo.expects.do_something
    foo.do_something
  end
  
  def should_accept_call_with_one_argument_when_arguments_unspecified
    foo = mock(Foo)
    foo.expects.do_another_thing
    foo.do_another_thing("A")
  end
  
  def should_accept_call_with_one_argument_when_argument_is_specified
    foo = mock(Foo)
    foo.expects.do_another_thing("A")
    foo.do_another_thing("A")
  end
  
  def should_reject_call_with_incorrect_argument_when_argument_is_specified
    foo = mock(Foo)
    foo.expects.do_another_thing("A")
    ensure_raises VerificationError do
      foo.do_another_thing("B")
    end
  end
  
  def should_fail_on_verify_if_expected_method_was_not_called
    foo = mock(Foo)
    foo.expects.do_something
    ensure_raises VerificationError do
      verify_mocks
    end
  end
  
  def should_accept_calls_for_multiple_expectations
    foo = mock(Foo)
    foo.expects.do_something("A")
    foo.expects.do_another_thing(_not("A"))
    foo.do_something("A")
    foo.do_another_thing("B")
  end
  
  def should_verify_multiple_expectations
    foo = mock(Foo)
    foo.expects.do_something("A")
    foo.expects.do_another_thing(_not("A"))
    foo.do_something("A")
    foo.do_another_thing("B")
  end
  
  def should_not_allow_expectation_for_unknown_method
    foo = mock(Foo)
    ensure_raises VerificationError do
      foo.expects.no_such_method
    end
  end
end