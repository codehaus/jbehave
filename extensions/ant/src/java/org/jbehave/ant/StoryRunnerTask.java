package org.jbehave.ant;

import org.apache.tools.ant.types.FileSet;
import org.jbehave.core.story.StoryRunner;

public class StoryRunnerTask extends AbstractStoryTask {
    
    public StoryRunnerTask() {
        this(new CommandRunnerImpl(), new TrimFilesetParser());
    }

    public StoryRunnerTask(CommandRunner runner, FilesetParser parser) {
        super(StoryRunner.class, runner, parser);
    }

    public void setStoryClassName(String storyClassName) {
        super.addTarget(storyClassName);
    }
        
    public void addStories(FileSet fileset) {
        super.addFilesetTarget(fileset);
    }
    
    public void setCloneVm(boolean cloneVm) {
        super.setCloneVm(cloneVm);
    }    

}
