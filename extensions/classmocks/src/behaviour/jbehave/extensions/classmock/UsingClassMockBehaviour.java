package jbehave.extensions.classmock;

import java.util.HashMap;

import jbehave.core.exception.PendingException;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.extensions.classmock.UsingClassMock;
import jbehave.extensions.classmock.ClassMockObjectBehaviour.AClassWithAReallyNastyConstructor;
import jbehave.extensions.classmock.ClassMockObjectBehaviour.AClassWithNoConstructorsAtAll;

public class UsingClassMockBehaviour extends UsingMiniMock {

	UsingClassMock classMock = new UsingClassMock();
	
	public void shouldBeAbleToMockClasses() {
		Object expected = new Object();

		Mock mock = classMock.mock(HashMap.class);
		mock.expects("get").with(anything()).will(returnValue(expected));
		
		Object actual = ((HashMap)mock).get("some key");
		ensureThat(expected, eq(actual));
	}
	
	public void shouldBeAbleToMockClassesProvidingConstructorArgs() {
		Object expected = new Object();

		Mock mock = classMock.mock(AClassWithAReallyNastyConstructor.class, "foo", 
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
		mock.expects("getSomething").will(returnValue(expected));
		
		Object actual = ((AClassWithAReallyNastyConstructor)mock).getSomething();
		ensureThat(expected, eq(actual));
	}
	
	public void shouldBeAbleToStubClasses() {
		throw new PendingException();
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
		
		public Object getSomething() {
			return null;
		}
    }
}
