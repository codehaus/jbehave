package org.jbehave.scenario.reporters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.jbehave.scenario.RunnableScenario;

/**
 * Creates {@link PrintStream} instance that write to a file. It also provides useful
 * defaults for reporting scenarios.
 */
public class FilePrintStreamFactory {

    private static final File DIR = new File("target", "scenario-reports");
    private static final String HTML = "html";
    private final File dir;
    private final String extension;

    public FilePrintStreamFactory() {
        this(DIR, HTML);
    }

    public FilePrintStreamFactory(File dir, String extension) {
        this.dir = dir;
        this.extension = extension;
    }

    public PrintStream createPrintStream(Class<? extends RunnableScenario> scenarioClass) {
       return createPrintStream(scenarioClass.getSimpleName());
    }

    public PrintStream createPrintStream(String scenarioName) {
        try {
            return new PrintStream(streamFile(dir, scenarioName, extension));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static File streamFile(File dir, String fileName, String extension) {
        dir.mkdirs();
        return new File(dir, fileName + "." + extension);
    }

}
