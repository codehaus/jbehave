package org.jbehave.ant;

import org.apache.tools.ant.types.FileSet;
import org.jbehave.core.story.StoryToDirectoryPrinter;

public class StoryPrinterTask extends AbstractStoryTask {

    public StoryPrinterTask() {
        super(StoryToDirectoryPrinter.class, new CommandRunnerImpl(), new TrimFilesetParser());
    }

    public void setDestDir(String dir) {
        super.addTarget(0, dir);
    }

    public void setStoryClassName(String storyClassName) {
        super.addTarget(storyClassName);
    }

    public void addStories(FileSet fileset) {
        super.addFilesetTarget(fileset);
    }
}
