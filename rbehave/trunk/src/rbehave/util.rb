# add Class.for_name
class Class
  def Class.for_name class_name
    cls = Module
    class_name.split(/::/).each {|m| cls = cls.const_get(m) }
    return cls
  end
end
