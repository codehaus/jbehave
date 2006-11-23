module RBehave
  class Runner
    def initialize(listener)
      @listener = listener
      @failures = []
      @methods_run = 0
      @listener.starting_run
    end
    
    def verify(cls)
      methods = cls.public_instance_methods.each do |method|
        next unless method =~ /^should/
        verify_method cls, method
      end
    end

    def verify_method(cls, method)
      begin
        @methods_run += 1
        Mocks.clear
        instance = cls.new
        instance.send method
        raise VerificationError, "#{method}: method does not contain Given/When/Then" unless instance.finished?
        putc '.' # TODO: move this into a listener
      rescue StandardError => error
        putc 'F'
        @failures << {:class => cls, :method => method, :error => error}
      end
    end
    
    def summarize
      summary = "#{@methods_run} method(s) run"
      summary += ", #{@failures.size} failure(s)" unless @failures.empty?
      puts "", summary, ""
      
      if @failures
        i = 1
        @failures.each do |failure|
          puts "#{i}) #{failure[:class].to_s.sub(/Behaviour$/, '')} #{as_text(failure[:method])}"
          puts "", failure[:error], failure[:error].backtrace, ""
        end
      end
    end
    
    def succeeded?
      @failures.empty?
    end
    
    private
    
    def as_text(method_name)
      method_name.gsub(/_/, ' ')
    end
  end
end