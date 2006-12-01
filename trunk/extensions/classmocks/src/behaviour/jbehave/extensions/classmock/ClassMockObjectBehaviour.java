package jbehave.extensions.classmock;

import java.util.HashMap;

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
	
	public static class AClassWithNoDeclaredConstructors {}

    
    public static class AClassWithAComplexConstructor {
        public AClassWithAComplexConstructor(
        		String anObject, 
        		int primitive, 
        		char primitive2, 
        		Object[] array) {
            anObject.compareTo("A string");
            int i = primitive + array.length;
            i++; // just to stop the unused warnings
        }
    }
    
    public static class AClassWithNoConstructorsAtAll {
    	public static final Object INSTANCE = new AClassWithNoConstructorsAtAll();
    	private AClassWithNoConstructorsAtAll() {}
    }
    
    public void shouldCreateClassObjectThatCanBeCastToTheCorrectType() {
        Mock mock = ClassMockObject.mockClass(AClass.class, "bar");
        Ensure.that(mock instanceof AClass);
    }
    
    public void shouldCreateClassObjectWhenClassRequiresArgumentsToConstructor() {
        ClassMockObject.mockClass(AClassWithConstructorArgs.class, "bar");
    }    
    
    public void shouldBeAbleToUseMockedClassesJustLikeOtherMocks() {
    	Object expected = new Object();
    	Mock mock = ClassMockObject.mockClass(HashMap.class, "hashMap");
		mock.expects("get").with("a key").will(returnValue(expected));
		
		Object actual = ((HashMap)mock).get("a key");
		ensureThat(expected, eq(actual));
    }

    public void shouldBeAbleToMockClassesWithUndeclaredConstructors() {
        ClassMockObject.mockClass(AClassWithNoDeclaredConstructors.class, "foo");
    }
    
    public void shouldBeAbleToMockMostClassesWithConstructorArgs() {
    	ClassMockObject.mockClass(AClassWithAComplexConstructor.class, "foo");
    }
}
