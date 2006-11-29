package jbehave.extensions.classmock;

import jbehave.core.exception.PendingException;

public class ConstructorFactoryBehaviour {

	public void shouldConstructStuff() {
		// TODO: The ConstructorFactory behaviour was pulled out of the ClassMockObject.
		// I haven't got round to writing the behaviour yet.
		// It may be worth injecting this into UsingClassMock; that way users
		// can provide default instantiations of eg: GlyphType.
		
		throw new PendingException();
	}
}
