package org.jbehave.classmock;

import java.util.HashMap;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;


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
        Object expected = new Object();

        Mock mock = classMock.mock(HashMap.class);
        mock.stubs("get").will(returnValue(expected));
        
        Object actual = ((HashMap)mock).get("some key");
        ensureThat(expected, eq(actual));
	}
}
