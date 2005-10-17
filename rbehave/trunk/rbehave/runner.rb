module RBehave
  class Runner
    def initialize
      @failures = []
      @methods_run = 0
    end
    
    def verify cls
      methods = cls.public_instance_methods.each { |method|
        next unless method =~ /^should/
        verify_method cls, method
      }
    end
    
    def verify_method cls, method
      begin
        @methods_run += 1
        cls.new.send method
        putc '.'
      rescue VerificationError => oops
        putc 'F'
        @failures << {:class => cls, :method => method, :error => oops}
      end
    end
    
    def summarize
      puts
      summary = "#{@methods_run} method(s) run"
      summary += ", #{@failures.size} failure(s)" unless @failures.empty?
      puts summary
      
      if @failures
        puts
        i = 1
        @failures.each {|failure|
          puts "#{i}) #{failure[:class].to_s.sub(/Behaviour$/, '')} #{failure[:method].gsub(/_/, ' ')}"
          puts
          puts failure[:error]
          puts
        }
      end
    end
  end
end