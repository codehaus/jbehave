/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.extensions.ant;

import jbehave.framework.Verify;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;

/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class JBehaveAntTaskSpec {
	private JBehaveAntTask task;
	private Project project;

	public void setUp() {
		task = new JBehaveAntTask();
		project = new Project();
		project.setCoreLoader(getClass().getClassLoader());
		task.setProject(project);
	}

	public void shouldRunASingleSpec() throws Exception {
		// setup
		Spec spec = task.createSpec();
		spec.setSpecName("jbehave.extensions.ant.SpecOne");

		// execute
		task.execute();

		// verify
		Verify.that(SpecOne.wasCalled);
	}


	public void shouldRunMulipleSpecs() throws Exception {
		// setup
		Spec spec = task.createSpec();
		spec.setSpecName("jbehave.extensions.ant.SpecOne");
		Spec spec2 = task.createSpec();
		spec2.setSpecName("jbehave.extensions.ant.SpecTwo");

		// execute
		task.execute();

		// verify
		Verify.that(SpecOne.wasCalled);
		Verify.that(SpecTwo.wasCalled);
	}


	public void shouldUseClasspathFromClasspathElement() throws Exception {
		// setup
		Path path = task.createClasspath();
		Path.PathElement element = path.createPathElement();
		element.setPath(".\\classes");
		task.createSpec().setSpecName("jbehave.extensions.ant.SpecOne");

		//execute
		task.execute();

		// verify
		Verify.that(SpecOne.wasCalled);
	}

	public void shouldFailTheBuildWhenCriteriaFails() throws Exception {
		// setup
		task.createSpec().setSpecName("jbehave.extensions.ant.FailingSpec");
		// execute
		try {
			task.execute();
			Verify.impossible("Should of failed the build");
		} catch (BuildException be) {
			Verify.that(be.getMessage().indexOf("jbehave.extensions.ant.FailingSpec") >= 0);
		}
	}

	public void shouldFailTheBuildWhenFirstSpecFails() throws Exception {
		// setup
		task.createSpec().setSpecName("jbehave.extensions.ant.FailingSpec");
		task.createSpec().setSpecName("jbehave.extensions.ant.SpecOne");
		SpecOne.wasCalled = false; // i hate this!

		// execute
		try {
			task.execute();
			Verify.impossible("Failing Spec should of failed the build");
		} catch (BuildException be) {
			Verify.that(be.getMessage().indexOf("jbehave.extensions.ant.FailingSpec") >= 0);
		}

		// verify
		Verify.that("SpecOne should not have been run", !SpecOne.wasCalled);
	}
	

}
