/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jbehave.ant;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.jbehave.core.Block;
import org.jbehave.core.minimock.UsingMiniMock;
import org.jbehave.core.mock.Matcher;


public class BehaviourRunnerTaskBehaviour extends UsingMiniMock {
    private BehaviourRunnerTask task;
    private StubCommandRunner runner = new StubCommandRunner();
    private StubFilesetParser filesetParser = new StubFilesetParser();

    public void setUp() {
        task = new BehaviourRunnerTask(runner, filesetParser);
        Project project = new Project();
        project.setCoreLoader(getClass().getClassLoader());
        task.setProject(project);
        Path path = task.createClasspath();
    }

    public void shouldRunASingleBehaviourClass() throws Exception {
        task.setBehavioursClassName(BehaviourClassOne.class.getName());
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
    
    public void shouldRunBehavioursFoundInFileSet() {
        
        FileSet fileSet = new FileSet();
        
        task.addBehaviours(fileSet);
        task.execute();

        List list = Arrays.asList(runner.commandLineLog);
        ensureThat(list, collectionContains(BehaviourClassOne.class.getName()));
        ensureThat(list, collectionContains(BehaviourClassTwo.class.getName()));
        
    }    

    public void shouldFailTheBuildWhenVerificationFails() throws Exception {
        final String behaviourClassName = FailingBehaviourClass.class.getName();
        task.setBehavioursClassName(behaviourClassName);
        runner.valueToReturn = 1;

        Exception exception = runAndCatch(BuildException.class, new Block() {
            public void run() throws Exception {
                task.execute();
            }
        });
        ensureThat(exception, isNotNull());
    }

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
