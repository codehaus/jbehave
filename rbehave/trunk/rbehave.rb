require 'rbehave/constraint'
require 'rbehave/mock'

require 'rubygems'
require 'breakpoint'

module RBehave
  class Behaviour
    include RBehave # all the stuff at RBehave module level
    include Constraints
    include Mocks
  end
  
  def initialize
    @__rbehave_state = :starting
  end
  
  def Given
    raise VerificationError unless @__rbehave_state == :starting
    yield
    @__rbehave_state = :done_given
  end
  
  def When
    raise VerificationError unless @__rbehave_state == :done_given
    begin
      yield
    rescue StandardError => e
      @__rbehave_invocation_error = e
    end
    @__rbehave_state = :done_when
  end
  
  def Then
    raise VerificationError unless @__rbehave_state == :done_when
    yield
    @__rbehave_state = :finished
  end
  
  def ensure_succeeds
    yield if block_given?
    ensure_succeeded
  end
  
  def ensure_succeeded
    unless @__rbehave_invocation_error.nil?
      raise @__rbehave_invocation_error
    end
  end
  
  def ensure_failed
    raise VerificationError if @__rbehave_invocation_error.nil?    
  end
  
  def ensure_raised(error)
    if @__rbehave_invocation_error.nil?
      raise VerificationError, "Should have raised #{error}"
      
    # wow, this is ugly! - DN
    elsif error.is_a? Class
      if not @__rbehave_invocation_error.is_a? error
        raise VerificationError, "Raised #{@__rbehave_invocation_error} instead of #{error}"
      end
    elsif @__rbehave_invocation_error != error
        raise VerificationError, "Raised #{@__rbehave_invocation_error} instead of #{error}"
      end
  end
  
  def finished?
    @__rbehave_state == :finished
  end
end

if ARGV[0] == 'describe'
  require 'rbehave/describe'
else
  require 'rbehave/execute'
end