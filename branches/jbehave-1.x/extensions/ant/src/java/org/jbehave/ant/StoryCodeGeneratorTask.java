package org.jbehave.ant;

import org.apache.tools.ant.types.FileSet;
import org.jbehave.core.story.codegen.velocity.VelocityCodeGenerator;

public class StoryCodeGeneratorTask extends AbstractStoryTask {

    public StoryCodeGeneratorTask() {
        this(new CommandRunnerImpl(), new TrimFilesetParser());
    }

    public StoryCodeGeneratorTask(CommandRunner runner, FilesetParser parser) {
        super(VelocityCodeGenerator.class, runner, parser);
    }

    public void setGeneratedSourceDirectory(String generatedSourceDirectory) {
        super.addTarget(generatedSourceDirectory);
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

    protected String[] getArguments(FileSet fileset, FilesetParser parser) {
        return parser.getRelativePaths(fileset, getProject());
    }

}
