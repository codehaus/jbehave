package jbehave.extensions.classmock;

import java.util.HashMap;

import jbehave.core.exception.PendingException;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;
import jbehave.extensions.classmock.UsingClassMock;

public class UsingClassMockBehaviour extends UsingMiniMock {

	UsingClassMock classMock = new UsingClassMock();
	
	public void shouldBeAbleToMockClasses() {
		Object expected = new Object();

		Mock mock = classMock.mock(HashMap.class);
		mock.expects("get").with(anything()).will(returnValue(expected));
		
		Object actual = ((HashMap)mock).get("some key");
		ensureThat(expected, eq(actual));
	}
	
	public void shouldBeAbleToStubClasses() {
		throw new PendingException();
	}	
}
