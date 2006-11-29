package jbehave.extensions.classmock;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

public class UsingClassMock extends UsingMiniMock {
    
	protected Mock createMock(Class type, String name) {
		return ClassMockObject.mockClass(type, name);
	}
	
	public Mock mock(Class type, Class[] argTypes, Object[] args) {
		return mock(type, type.getName(), argTypes, args);
	}
	
	public Mock mock(Class type, String name, Class[] argTypes, Object[] args) {
		return ClassMockObject.mockClass(type, name, argTypes, args);
	}
}
