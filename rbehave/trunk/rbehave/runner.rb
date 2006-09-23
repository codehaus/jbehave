module RBehave
  class Runner
    def initialize
      @failures = []
      @methods_run = 0
    end
    
    def verify(cls)
      methods = cls.public_instance_methods.each do |method|
        next unless method =~ /^should/
        verify_method cls, method
      end
    end
    
    def verify_method cls, method
      begin
        @methods_run += 1
        cls.new.send method
        putc '.'
      rescue Exception => error
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
          puts "#{i}) #{failure[:class].to_s.sub(/Behaviour$/, '')} #{failure[:method].gsub(/_/, ' ')}"
          puts "", failure[:error], failure[:error].backtrace, ""
        end
      end
    end
  end
end