package org.jbehave.ant;

import org.apache.tools.ant.types.FileSet;
import org.jbehave.core.story.StoryRunner;

public class JBehaveStoryTask extends AbstractRunnerTask {
    
    public JBehaveStoryTask() {
        this(new CommandRunnerImpl(), new TrimFilesetParser());
    }

    public JBehaveStoryTask(CommandRunner runner, FilesetParser parser) {
        super(StoryRunner.class, runner, parser);
    }

    public void setStoryClassName(String storyClassName) {
        super.addTarget(storyClassName);
    }
    
    
    public void addStories(FileSet fileset) {
        super.addFilesetTarget(fileset);
    }

}
