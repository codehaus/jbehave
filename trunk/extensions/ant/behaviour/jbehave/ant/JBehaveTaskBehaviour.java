/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.ant;

import jbehave.core.Run;
import jbehave.core.minimock.UsingMiniMock;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;
import java.io.File;
import java.util.Arrays;

import net.sf.cotta.utils.ClassPathLocator;


/**
 * @author <a href="mailto:damian.guy@thoughtworks.com">Damian Guy</a>
 *         Date: 19-Jul-2004
 */
public class JBehaveTaskBehaviour extends UsingMiniMock {
    private JBehaveTask task;
    private StubCommandRunner runner = new StubCommandRunner();

    public void setUp() {
        task = new JBehaveTask(runner);
        Project project = new Project();
        project.setCoreLoader(getClass().getClassLoader());
        task.setProject(project);
        Path path = task.createClasspath();
        addToPathContains(path, getClass());
        addToPathContains(path, Run.class);
    }

    private void addToPathContains(Path path, Class aClass) {
        ClassPathLocator behaviourClassPathLocator = new ClassPathLocator(aClass);
        path.createPathElement().setLocation(new File(behaviourClassPathLocator.locate().path()));
    }

    public void shouldRunASingleBehaviourClass() throws Exception {
        BehaviourClassDetails behaviourClass = task.createVerify();
        behaviourClass.setName(BehaviourClassOne.class.getName());
        runner.valueToReturn = 0;

        task.execute();

        ensureThat(runner.taskLog, sameInstanceAs(task));
        String[] actualCommand = runner.commandLineLog;
        ensureThat(actualCommand[0].endsWith("java.exe"), eq(true));
        System.out.println(Arrays.asList(actualCommand));
    }
/*
    public void shouldRunMultipleBehaviourClasses() throws Exception {
        // setup
        BehaviourClassDetails spec = task.createVerify();
        spec.setName(BehaviourClassOne.class.getName());
        BehaviourClassDetails spec2 = task.createVerify();
        spec2.setName(BehaviourClassTwo.class.getName());

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
        task.createVerify().setName(BehaviourClassOne.class.getName());

        //execute
        task.execute();

        // verify
        Ensure.that(BehaviourClassOne.wasCalled);
    }

    public void shouldFailTheBuildWhenVerificationFails() throws Exception {
        // setup
        final String behaviourClassName = FailingBehaviourClass.class.getName();
        task.createVerify().setName(behaviourClassName);
        // execute
        ensureThrows(BuildException.class, new Block() {
            public void run() throws Exception {
                task.execute();
            }
        });
    }

    public void shouldFailTheBuildWhenFirstSpecFails() throws Exception {
        // setup
        task.createVerify().setName("jbehave.extensions.ant.FailingSpec");
        task.createVerify().setName("jbehave.extensions.ant.SpecOne");
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

*/

    private static class StubCommandRunner implements CommandRunner {
        private int valueToReturn;
        private Task taskLog;
        private String[] commandLineLog;

        public int fork(Task task, String[] commandline) {
            taskLog = task;
            commandLineLog = commandline;
            return valueToReturn;
        }
    }
}
