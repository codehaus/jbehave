module RBehave
  module Utils
    # TODO make this work off a range of boring entries
    def clean_backtrace(backtrace = caller, boring = -7..-2)
      [backtrace[1,backtrace.size-6], backtrace[-2]].flatten
    end
  end
end