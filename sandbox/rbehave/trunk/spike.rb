class Foo
end

foo = Foo.new

puts foo.respond_to?(:method_missing)
puts foo.respond_to?(:blah)

class Foo
  def blah; end
  
  def method_missing(m, *args); end
end

puts foo.respond_to?(:method_missing)
puts foo.respond_to?(:blah)
