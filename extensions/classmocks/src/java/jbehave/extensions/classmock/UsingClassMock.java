package jbehave.extensions.classmock;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

public class UsingClassMock extends UsingMiniMock {
    
	protected Mock createMock(Class type, String name) {
		return ClassMockObject.mockClass(type, name);
	}
}
