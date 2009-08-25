module RBehave
  class VerificationError < StandardError
  end
  
  class NestedVerificatonError < VerificationError
    attr_reader :current, :nested
    
    def initialize(current, nested, message = self.class.to_s)
      super(message)
#      set_backtrace(current.backtrace)
      @current, @nested = current, nested
    end
    
#    def excepion(current, nested, message = self.class.to_s)
#      new(current, nested, message)
#    end
    
    def to_s
      "[#{self.class}: #@current possibly caused by #{@nested.class} #@nested]"
    end
  end
end
