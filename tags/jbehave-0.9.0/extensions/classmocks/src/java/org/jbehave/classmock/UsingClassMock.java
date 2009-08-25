package org.jbehave.classmock;

import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Mock;

public class UsingClassMock extends UsingMiniMock {
    
	protected Mock createMock(Class type, String name) {
		return ClassMockObject.mockClass(type, name);
	}
}
