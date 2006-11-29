package jbehave.extensions.classmock;

import java.util.HashMap;

import jbehave.core.Block;
import jbehave.core.Ensure;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

public class ClassMockObjectBehaviour extends UsingMiniMock {
	public static class AClass {}
	
	public class ANonStaticInnerClass {}
	
	public static class AClassWithConstructorArgs {
		public AClassWithConstructorArgs(Object arg) {
			
		}
	}
	
	private static class AClassWithNoConstructors {}
	
    public void shouldCreateClassObjectThatCanBeCastToTheCorrectType() {
        Mock mock = ClassMockObject.mockClass(AClass.class, "bar");
        Ensure.that(mock instanceof AClass);
    }
    
    public void shouldCreateClassObjectWhenClassRequiresArgumentsToConstructor() {
        ClassMockObject.mockClass(AClassWithConstructorArgs.class, "bar");
    }    
    
    public void shouldThrowAnIllegalArgumentExceptionIfClassIsStaticInnerClass() throws Exception {
    	Ensure.throwsException(IllegalArgumentException.class, new Block() {
			public void run() throws Exception {
				ClassMockObject.mockClass(ANonStaticInnerClass.class, "bar");
			}
    	});
    }
    
    public void shouldThrowAnIllegalArgumentExceptionIfClassHasNoConstructors() throws Exception {
    	Ensure.throwsException(IllegalArgumentException.class, new Block() {
			public void run() throws Exception {
				ClassMockObject.mockClass(AClassWithNoConstructors.class, "bar");
			}
    	});
    }
    
    public void shouldBeAbleToUseMockedClassesJustLikeOtherMocks() {
    	Object expected = new Object();
    	Mock mock = ClassMockObject.mockClass(HashMap.class, "hashMap");
		mock.expects("get").with("a key").will(returnValue(expected));
		
		Object actual = ((HashMap)mock).get("a key");
		ensureThat(expected, eq(actual));
    }
        
    
}
