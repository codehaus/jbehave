/*
 * Created on 16-July-2004
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.extensions.jmock;


/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 16-Jul-2004
 */
public class ExampleMockBehaviour implements JMockable {

	private Mock aMock;

	interface Dependency {
		String invokeMe();
	}

	public class ClassWithDependency {
		private Dependency dep;

		public ClassWithDependency(Dependency dep) {
			this.dep = dep;
		}

		public void execute() {
        	dep.invokeMe();
		}
	}

	public void shouldUseAMock() {
		// setup
		Mock dependencyMock = new Mock(Dependency.class);
		ClassWithDependency a = new ClassWithDependency((Dependency) aMock.proxy());
		// expect
		dependencyMock.expects(Invoked.once()).
				method("invokeMe").
				withNoArguments().
				will(Return.value("hello"));
		// execute
		a.execute();
		// verify  happens auto-magically
	}
}
