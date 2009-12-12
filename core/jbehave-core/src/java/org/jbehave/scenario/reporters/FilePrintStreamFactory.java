package org.jbehave.scenario.reporters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Creates {@link PrintStream} instances that write to a file. It also provides
 * useful defaults for the file directory and the extension.
 */
public class FilePrintStreamFactory implements PrintStreamFactory {

    static final String HTML = "html";
    private PrintStream printStream;

    public FilePrintStreamFactory(Class<?> scenarioClass) {
        this(scenarioClass, HTML);
    }

    public FilePrintStreamFactory(Class<?> scenarioClass, String fileExt) {
        this(outputDirectory(scenarioClass), fileName(scenarioClass, fileExt));
    }

    public FilePrintStreamFactory(File outputDirectory, String fileName) {
        outputDirectory.mkdirs();
        try {
            printStream = new PrintStream(new FileOutputStream(new File(outputDirectory, fileName), true));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    static File outputDirectory(Class<?> scenarioClass) {
        String classesDir = scenarioClass.getProtectionDomain().getCodeSource().getLocation().getFile();
        File targetDirectory = new File(classesDir).getParentFile();
        return new File(targetDirectory, "scenario-reports");
    }

    static String fileName(Class<?> scenarioClass, String fileExt) {
        return scenarioClass.getName() + "." + fileExt;
    }

}
