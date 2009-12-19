package org.jbehave.scenario.reporters;

import org.jbehave.scenario.RunnableScenario;
import org.jbehave.scenario.parser.ScenarioNameResolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * Creates {@link PrintStream} instances that write to a file. It also provides
 * useful defaults for the file directory and the extension.
 */
public class FilePrintStreamFactory implements PrintStreamFactory {

    private File outputDirectory;
    private PrintStream printStream;

    public FilePrintStreamFactory(Class<? extends RunnableScenario> scenarioClass,
            ScenarioNameResolver scenarioNameResolver) {
        this(scenarioClass, scenarioNameResolver, new FileConfiguration());
    }

    public FilePrintStreamFactory(Class<? extends RunnableScenario> scenarioClass,
            ScenarioNameResolver scenarioNameResolver, FileConfiguration configuration) {
        this(outputDirectory(scenarioClass, configuration),
                fileName(scenarioClass, scenarioNameResolver, configuration));
    }

    public FilePrintStreamFactory(File outputDirectory, String fileName) {        
        this.outputDirectory = outputDirectory;
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

    public File getOutputDirectory() {
        return outputDirectory;
    }

    static File outputDirectory(Class<? extends RunnableScenario> scenarioClass, FileConfiguration configuration) {
        String classesDir = scenarioClass.getProtectionDomain().getCodeSource().getLocation().getFile();        
        File targetDirectory = new File(classesDir).getParentFile();
        return new File(targetDirectory, configuration.getDirectory());
    }

    static String fileName(Class<? extends RunnableScenario> scenarioClass, ScenarioNameResolver scenarioNameResolver,
            FileConfiguration configuration) {
        String scenarioName = scenarioNameResolver.resolve(scenarioClass).replace('/', '.');
        String name = scenarioName.substring(0, scenarioName.lastIndexOf("."));
        return name + "." + configuration.getExtension();
    }

    /**
     * Configuration class for file print streams. Allows specification of the
     * file directory (relative to the scenario class code source location) and
     * the file extension. Provides as defaults {@link #DIRECTORY} and
     * {@link #HTML}.
     */
    public static class FileConfiguration {
        public static final String DIRECTORY = "jbehave-reports";
        public static final String HTML = "html";

        private final String directory;
        private final String extension;

        public FileConfiguration() {
            this(DIRECTORY, HTML);
        }

        public FileConfiguration(String extension) {
            this(DIRECTORY, extension);
        }

        public FileConfiguration(String directory, String extension) {
            this.directory = directory;
            this.extension = extension;
        }

        public String getDirectory() {
            return directory;
        }

        public String getExtension() {
            return extension;
        }

    }
}
