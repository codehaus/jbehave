package org.jbehave.ant;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.jbehave.core.Block;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers;

public class StoryRunnerTaskBehaviour extends UsingMatchers {

    private StubCommandRunner runner = new StubCommandRunner();
    private StoryRunnerTask task = new StoryRunnerTask();
    private StubFilesetParser filesetParser = new StubFilesetParser();
    
    public void setUp() {
        task = new StoryRunnerTask(runner, filesetParser);
        Project project = new Project();
        project.setCoreLoader(getClass().getClassLoader());
        task.setProject(project);
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
    
    public void shouldRunASingleStoryClass() throws Exception {
        task.setStoryClassName(StoryClassOne.class.getName());
        runner.valueToReturn = 0;

        task.execute();

        ensureThat(runner.taskLog, sameInstanceAs(task));
        String[] actualCommand = runner.commandLineLog;
        ensureThat(actualCommand[0].matches(".*java.*"), eq(true));
        List list = Arrays.asList(actualCommand);
        ensureThat(list, collectionContains(StoryClassOne.class.getName()));
    }
    
   public void shouldRunStoriesFoundInFileSet() {
        
        FileSet fileSet = new FileSet();
        
        task.addStories(fileSet);
        task.execute();

        List list = Arrays.asList(runner.commandLineLog);
        ensureThat(list, collectionContains(StoryClassOne.class.getName()));
        ensureThat(list, collectionContains(StoryClassTwo.class.getName()));
    }    

    public void shouldFailTheBuildWhenVerificationFails() throws Exception {
        final String storyClassName = FailingStoryClass.class.getName();
        task.setStoryClassName(storyClassName);
        runner.valueToReturn = 1;

        Exception exception = runAndCatch(BuildException.class, new Block() {
            public void run() throws Exception {
                task.execute();
            }
        });
        ensureThat(exception, isNotNull());
    }
    
    public void shouldSupportCloningTheVm() throws Exception {
        
        FileSet fileSet = new FileSet();
        
        task.addStories(fileSet);
        task.setCloneVm(true);
        task.execute();

        List list = Arrays.asList(runner.commandLineLog);
        ensureThat(list, collectionContains(StoryClassOne.class.getName()));
        ensureThat(list, collectionContains(StoryClassTwo.class.getName()));
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
                    StoryClassOne.class.getName(),
                    StoryClassTwo.class.getName()
            };
        }
    }    
}
