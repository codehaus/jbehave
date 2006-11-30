package jbehave.extensions.classmock;

import java.util.HashMap;

import jbehave.core.Block;
import jbehave.core.Ensure;
import jbehave.core.exception.PendingException;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

public class ClassMockObjectBehaviour extends UsingMiniMock {
	public static class AClass {}
	
	public class ANonStaticInnerClass {}
	
	public static class AClassWithConstructorArgs {
		public AClassWithConstructorArgs(Object arg) {
			
		}
	}
	
	private static class AClassWithNoDeclaredConstructors {}

    
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
    
    public static class AClassWithAReallyNastyConstructor {     
		public AClassWithAReallyNastyConstructor(
        		String anObject, 
        		int primitive, 
        		char primitive2, 
        		Object[] array, 
        		AClassWithNoConstructorsAtAll someEnum) {
            anObject.compareTo(someEnum.toString());
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
				ClassMockObject.mockClass(AClassWithNoDeclaredConstructors.class, "bar");
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
    
    public void shouldBeAbleToProvideDefaultInstancesForConstructorClasses() {
    	Mock mock = ClassMockObject.mockClass(
    			AClassWithAReallyNastyConstructor.class, "foo", 
    			new Class[] {
    				String.class, 
            		int.class, 
            		char.class, 
            		Object[].class, 
            		AClassWithNoConstructorsAtAll.class}, 
    			new Object[] {
    				"",
    				new Integer(0),
    				new Character(' '),
    				new Object[0],
    				AClassWithNoConstructorsAtAll.INSTANCE});
    }
        
    public void shouldBeAbleToMockMostClassesWithConstructorArgs() {
    	ClassMockObject.mockClass(AClassWithAComplexConstructor.class, "foo");
    }
    
    public void shouldRethrowNullPointerExceptionsWithSuggestionWhenConstructorFails() throws Exception {
    	ensureThrows(IllegalArgumentException.class, new Block() {
			public void run() throws Exception {
				ClassMockObject.mockClass(AClassWithAReallyNastyConstructor.class, "foo");
			}
    	});
    }
    
    public void shouldBeAbleToProvideMinimalProblemClassConstructorsAndHaveTheFactoryFillInTheRest() {
    	throw new PendingException();
    }
}
