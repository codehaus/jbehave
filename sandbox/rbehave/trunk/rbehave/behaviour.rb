require 'rbehave/matcher'
require 'rbehave/mock'

module RBehave
    
  # Marker module for behaviours that should not be run automagically
  module DoNotAutorun
  end
  
  class Behaviour
#    include RBehave # all the stuff at RBehave module level
    include Matchers
    include Mocks
    
    def initialize
      @__jbehave_state = :starting
      @__jbehave_checked_for_failure = false
    end
    
    def finished?
      @__jbehave_state == :finished
    end
    
    protected
    
    def jbehave_error
      @__jbehave_error
    end
    
    def _given
      raise VerificationError, "'_given' not expected here" unless @__jbehave_state == :starting
      yield
      @__jbehave_state = :done_given
    end
    
    def _when
      raise VerificationError, "'_when' not expected here" unless @__jbehave_state == :done_given
      begin
        yield
      rescue StandardError => e
        @__jbehave_error = e
      end
      @__jbehave_state = :done_when
    end
    
    def _then
      raise VerificationError, "'_then' not expected here" unless @__jbehave_state == :done_when
      begin
        yield
        ensure_succeeded unless @__jbehave_checked_for_failure
        @__jbehave_state = :finished
      rescue StandardError => e
        raise e # @__jbehave_error ? NestedVerificatonError.new(e, @__jbehave_error) : e
      end
    end
  
    def ensure_succeeded
      unless @__jbehave_error.nil?
        raise @__jbehave_error
      end
      @__jbehave_checked_for_failure = true
    end
    
    def ensure_failed
      raise VerificationError if @__jbehave_error.nil?
      @__jbehave_checked_for_failure = true
    end
    
    def ensure_raised(error) # error can be an error instance or an Exception class
      if @__jbehave_error.nil?
        raise VerificationError, "Should have raised #{error}"
      # wow, this is ugly! - DN
      elsif error.is_a? Class
        if not @__jbehave_error.is_a? error
          raise VerificationError, "Raised #{@__jbehave_error} instead of #{error}"
        end
      elsif @__jbehave_error != error
        raise VerificationError, "Raised #{@__jbehave_error} instead of #{error}"
      end
      @__jbehave_checked_for_failure = true
    end
  end  
end
