package org.jbehave.scenario.parser;

import java.io.InputStream;

import org.jbehave.scenario.Scenario;

public class FileFinder {

    private final ClassToFilenameConverter converter;
    private final ClassLoader classLoader;

    public FileFinder() {
        this(new CamelCaseToUnderscoreConverter(), Thread.currentThread().getContextClassLoader());
    }

    public FileFinder(ClassToFilenameConverter converter) {
        this(converter, Thread.currentThread().getContextClassLoader());
    }

    public FileFinder(ClassLoader classLoader) {
        this(new CamelCaseToUnderscoreConverter(), classLoader);
    }

    public FileFinder(ClassToFilenameConverter converter, ClassLoader classLoader) {
        this.converter = converter;
        this.classLoader = classLoader;
    }

    public InputStream findFileMatching(Class<? extends Scenario> scenarioClass) {
        String scenarioFilename = converter.convert(scenarioClass);
        return classLoader.getResourceAsStream(scenarioFilename);
    }

}
