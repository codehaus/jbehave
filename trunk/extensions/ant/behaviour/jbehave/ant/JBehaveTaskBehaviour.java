/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.ant;

import jbehave.core.Block;
import jbehave.core.Ensure;
import jbehave.core.minimock.UsingConstraints;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Path;


/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class JBehaveTaskBehaviour extends UsingConstraints {
	private JBehaveTask task;
	private Project project;

	public void setUp() {
		task = new JBehaveTask();
		project = new Project();
		project.setCoreLoader(getClass().getClassLoader());
		task.setProject(project);
	}

	public void shouldRunASingleBehaviourClass() throws Exception {
        // setup
		BehaviourClassDetails behaviourClass = task.createBehaviourClass();
		behaviourClass.setBehaviourClassName(BehaviourClassOne.class.getName());

		// execute
		task.execute();

		// verify
		Ensure.that(BehaviourClassOne.wasCalled);
	}

	public void shouldRunMultipleBehaviourClasses() throws Exception {
		// setup
		BehaviourClassDetails spec = task.createBehaviourClass();
		spec.setBehaviourClassName(BehaviourClassOne.class.getName());
		BehaviourClassDetails spec2 = task.createBehaviourClass();
		spec2.setBehaviourClassName(BehaviourClassTwo.class.getName());

		// execute
		task.execute();

		// verify
		Ensure.that(BehaviourClassOne.wasCalled);
		Ensure.that(BehaviourClassTwo.wasCalled);
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
		Ensure.that(BehaviourClassOne.wasCalled);
	}

	public void shouldFailTheBuildWhenVerificationFails() throws Exception {
		// setup
		final String behaviourClassName = FailingBehaviourClass.class.getName();
        task.createBehaviourClass().setBehaviourClassName(behaviourClassName);
		// execute
        ensureThrows(BuildException.class, new Block() {
            public void run() throws Exception {
                task.execute();
            }
        });
	}

	public void shouldFailTheBuildWhenFirstSpecFails() throws Exception {
		// setup
		task.createBehaviourClass().setBehaviourClassName("jbehave.extensions.ant.FailingSpec");
		task.createBehaviourClass().setBehaviourClassName("jbehave.extensions.ant.SpecOne");
		BehaviourClassOne.wasCalled = false; // i hate this!

		// execute
        ensureThrows(BuildException.class, new Block() {
            public void run() {
                task.execute();
            }
        });

		// verify
		Ensure.that("SpecOne should not have been run", !BehaviourClassOne.wasCalled);
	}
	

}
