package org.jbehave.core.story;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.jbehave.core.story.domain.Story;
import org.jbehave.core.story.renderer.PlainTextRenderer;

public class StoryToDirectoryPrinter {

    private final StoryLoader storyLoader;
    private final File directory;

    public StoryToDirectoryPrinter(StoryLoader storyLoader, File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException("Directory " + directory + " does not exist.");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Directory " + directory + " is not a directory.");
        }

        this.storyLoader = storyLoader;
        this.directory = directory;
    }

    public void print(String storyClassName) throws IOException {
        Story story = storyLoader.loadStoryClass(storyClassName);
        String simpleStoryName = simpleStoryName(story);

        File file = new File(directory, simpleStoryName + ".story");
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream outputStream = new FileOutputStream(file);
        new StoryPrinter(storyLoader, new PlainTextRenderer(new PrintStream(outputStream))).print(storyClassName);

        outputStream.close();
    }

    private String simpleStoryName(Story story) {
        String[] storyNameParts = story.getClass().getName().split("\\.");
        return storyNameParts[storyNameParts.length - 1];
    }

    public static void main(String[] args) throws IOException {
        File directory = new File(args[0]);
        StoryToDirectoryPrinter printer = new StoryToDirectoryPrinter(new StoryLoader(), directory);

        for (int i = 1; i < args.length; i++) {
            printer.print(args[i]);
        }
    }
}
