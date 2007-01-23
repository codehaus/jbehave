/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jbehave.ant;


import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.sf.cotta.utils.ClassPath;
import net.sf.cotta.utils.ClassPathLocator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.jbehave.core.Block;
import org.jbehave.core.Run;
import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Matcher;


public class JBehaveTaskBehaviour extends UsingMiniMock {
    private JBehaveTask task;
    private StubCommandRunner runner = new StubCommandRunner();
    private StubFilesetParser filesetParser = new StubFilesetParser();

    public void setUp() {
        task = new JBehaveTask(runner, filesetParser);
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
        ensureThat(actualCommand[0].matches(".*java.*"), eq(true));
        List list = Arrays.asList(actualCommand);
        ensureThat(list, collectionContains(BehaviourClassOne.class.getName()));
    }

    private Matcher collectionContains(final Object item) {
        return new Matcher() {
            public boolean matches(Object arg) {
                return ((Collection)arg).contains(item);
            }

            public String toString() {
                return "Collection that contains <" + item + ">";
            }
        };
    }

    public void shouldRunMultipleBehaviourClasses() throws Exception {
        BehaviourClassDetails spec = task.createVerify();
        spec.setName(BehaviourClassOne.class.getName());
        BehaviourClassDetails spec2 = task.createVerify();
        spec2.setName(BehaviourClassTwo.class.getName());
        runner.valueToReturn = 0;

        task.execute();

        List list = Arrays.asList(runner.commandLineLog);
        ensureThat(list, collectionContains(BehaviourClassOne.class.getName()));
        ensureThat(list, collectionContains(BehaviourClassTwo.class.getName()));
    }
    
    public void shouldRunBehavioursFoundInFileSet() {
        
        FileSet fileSet = new FileSet();
        
        task.addBehaviours(fileSet);
        task.execute();

        List list = Arrays.asList(runner.commandLineLog);
        ensureThat(list, collectionContains(BehaviourClassOne.class.getName()));
        ensureThat(list, collectionContains(BehaviourClassTwo.class.getName()));
        
    }    

    public void shouldUseClasspathFromClasspathElement() throws Exception {
        Path path = task.createClasspath();
        Path.PathElement element = path.createPathElement();
        
        ClassPath classPath = new ClassPathLocator(String.class).locate();
        String pathToRuntimeJar = classPath.path();
        element.setPath(pathToRuntimeJar);
        
        task.createVerify().setName(BehaviourClassOne.class.getName());

        task.execute();

        List list = Arrays.asList(runner.commandLineLog);
        int classPathSwitchElement = list.indexOf("-classpath");
        ensureThat(classPathSwitchElement, not(eq(-1)));
        String classPaths = (String) list.get(classPathSwitchElement + 1);
        String[] classPathArray = classPaths.split(File.pathSeparator);
        ensureThat(Arrays.asList(classPathArray), collectionContains(pathToRuntimeJar));
    }

    public void shouldFailTheBuildWhenVerificationFails() throws Exception {
        final String behaviourClassName = FailingBehaviourClass.class.getName();
        task.createVerify().setName(behaviourClassName);
        runner.valueToReturn = 1;

        Exception exception = runAndCatch(BuildException.class, new Block() {
            public void run() throws Exception {
                task.execute();
            }
        });
        ensureThat(exception, isNotNull());
    }

    
/* TODO
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
    
    private static class StubFilesetParser implements FilesetParser {
        
        public String[] getClassNames(FileSet fileset, Project project) {
            return new String[] {
                    BehaviourClassOne.class.getName(),
                    BehaviourClassTwo.class.getName()
            };
        }
    }
}
