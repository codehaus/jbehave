/*
 * Created on 16-July-2004
 *
 * (c) 2003-2004 ThoughtWorks
 *
 * See license.txt for licence details
 */
package jbehave.extensions.jmock;

import org.jmock.Mock;
import org.jmock.core.matcher.InvokeOnceMatcher;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 16-Jul-2004
 */
public class ExampleMockSpec {
	private Mock aMock;
	private Mocker mocker;

	interface ADependency {
		void invokeMe();
	}

	public class AclassWithADependency {
		private ADependency dep;

		public AclassWithADependency(ADependency dep) {
			this.dep = dep;
		}

		public void execute() {
        	dep.invokeMe();
		}
	}



	public void needsMocks(Mocker mocker) {
		this.mocker = mocker;
	}

	public void shouldUseAMock() {
		// setup
		aMock = mocker.mock(ADependency.class);

		AclassWithADependency a = new AclassWithADependency((ADependency) aMock.proxy());

		// expect
		aMock.expects(new InvokeOnceMatcher()).method("invokeMe");

		// execute
		a.execute();

		// verify
	}
}
