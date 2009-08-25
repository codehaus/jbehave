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

public class StoryCodeGeneratorTaskBehaviour extends UsingMatchers {

    private StubCommandRunner runner = new StubCommandRunner();
    private StoryCodeGeneratorTask task = new StoryCodeGeneratorTask();
    private StubFilesetParser filesetParser = new StubFilesetParser();
    
    public void setUp() {
        task = new StoryCodeGeneratorTask(runner, filesetParser);
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
       
   public void shouldGenerateCodeForStoriesFoundInFileSet() {
        
        FileSet fileSet = new FileSet();

        task.setGeneratedSourceDirectory("stories");
        task.addStories(fileSet);
        task.execute();
        ensureThat(runner.taskLog, sameInstanceAs(task));
        
        List list = Arrays.asList(runner.commandLineLog);
        ensureThat(list, collectionContains("stories"));
        ensureThat(list, collectionContains("one.story"));
        ensureThat(list, collectionContains("two.story"));
    }    

    public void shouldFailTheBuildWhenVerificationFails() throws Exception {
        final String generatedSourceDirectory = "stories";
        task.setGeneratedSourceDirectory(generatedSourceDirectory);
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
            return new String[] {};
        }
        
        public String[] getRelativePaths(FileSet fileset, Project project) {
            return new String[] {"one.story", "two.story"};
        }            
    }    
}
