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
public class AntTaskBehaviour {
	private AntTask task;
	private Project project;

	public void setUp() {
		task = new AntTask();
		project = new Project();
		project.setCoreLoader(getClass().getClassLoader());
		task.setProject(project);
	}

	public void shouldRunASingleBehaviourClass() throws Exception {
		// setup
		BehaviourClass spec = task.createBehaviourClass();
		spec.setBehaviourClassName("jbehave.extensions.ant.BehaviourClassOne");

		// execute
		task.execute();

		// verify
		Verify.that(BehaviourClassOne.wasCalled);
	}


	public void shouldRunMultipleBehaviourClasses() throws Exception {
		// setup
		BehaviourClass spec = task.createBehaviourClass();
		spec.setBehaviourClassName("jbehave.extensions.ant.BehaviourClassOne");
		BehaviourClass spec2 = task.createBehaviourClass();
		spec2.setBehaviourClassName("jbehave.extensions.ant.BehaviourClassTwo");

		// execute
		task.execute();

		// verify
		Verify.that(BehaviourClassOne.wasCalled);
		Verify.that(BehaviourClassTwo.wasCalled);
	}


	public void shouldUseClasspathFromClasspathElement() throws Exception {
		// setup
		Path path = task.createClasspath();
		Path.PathElement element = path.createPathElement();
		element.setPath(".\\classes");
		task.createBehaviourClass().setBehaviourClassName(BehaviourClassOne.class.getName());

		//execute
		task.execute();

		// verify
		Verify.that(BehaviourClassOne.wasCalled);
	}

	public void shouldFailTheBuildWhenCriteriaFails() throws Exception {
		// setup
		final String behaviourClassName = FailingBehaviourClass.class.getName();
        task.createBehaviourClass().setBehaviourClassName(behaviourClassName);
		// execute
		try {
			task.execute();
			Verify.impossible("Should have failed the build");
		} catch (BuildException e) {
			Verify.that(e.getMessage().indexOf(behaviourClassName) >= 0);
		}
	}

	public void shouldFailTheBuildWhenFirstSpecFails() throws Exception {
		// setup
		task.createBehaviourClass().setBehaviourClassName("jbehave.extensions.ant.FailingSpec");
		task.createBehaviourClass().setBehaviourClassName("jbehave.extensions.ant.SpecOne");
		BehaviourClassOne.wasCalled = false; // i hate this!

		// execute
		try {
			task.execute();
			Verify.impossible("Failing Spec should of failed the build");
		} catch (BuildException be) {
			Verify.that(be.getMessage().indexOf("jbehave.extensions.ant.FailingSpec") >= 0);
		}

		// verify
		Verify.that("SpecOne should not have been run", !BehaviourClassOne.wasCalled);
	}
	

}
