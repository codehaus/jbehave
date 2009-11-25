package org.jbehave.scenario.reporters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Creates {@link PrintStream} instances that write to a file. It also provides useful
 * defaults for the file directory and the extension.
 */
public class FilePrintStreamFactory implements PrintStreamFactory {

    private static final File DEFAULT_DIRECTORY = new File("target", "scenario-reports");
    private static final String HTML = "html";
    private final File directory;
    private final String extension;

    public FilePrintStreamFactory() {
        this(DEFAULT_DIRECTORY, HTML);
    }

    public FilePrintStreamFactory(File directory, String extension) {
        this.directory = directory;
        this.extension = extension;
    }

    public PrintStream createPrintStream(String storyName) {
        try {
            return new PrintStream(fileFor(directory, storyName, extension));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private File fileFor(File dir, String name, String ext) {
        dir.mkdirs();
        return new File(dir, name + "." + ext);
    }

}
