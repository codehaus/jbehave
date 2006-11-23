=begin
  Print each Behaviour and test method as it is loaded by the interpreter
  Author: Dan North
=end

module RBehave
  class Behaviour
    def Behaviour.inherited(subclass) # intercept behaviour classes
      return if subclass.to_s.index('::') # ignore sub-behaviours [hack for behaviours inside rbehave itself]
      printf "\n%s\n", subclass.to_s.sub(/(Behaviou?r|Spec)$/, "")
      eval <<-EOM
        def #{subclass}.method_added(id) # intercept behaviour methods
          meth = id.to_s
          printf("- %s\n", meth.gsub(/_/, ' ')) if meth =~ /^should/
        end
      EOM
    end
  end
end
