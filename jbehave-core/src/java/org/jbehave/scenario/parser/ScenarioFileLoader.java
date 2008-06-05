package org.jbehave.scenario.parser;

import java.io.InputStream;

import org.jbehave.scenario.Scenario;

public class ScenarioFileLoader {

    private final ClassToFilenameConverter converter;
    private final ClassLoader classLoader;

    public ScenarioFileLoader() {
        this(new CamelCaseToUnderscoreConverter(), Thread.currentThread().getContextClassLoader());
    }

    public ScenarioFileLoader(ClassToFilenameConverter converter) {
        this(converter, Thread.currentThread().getContextClassLoader());
    }

    public ScenarioFileLoader(ClassLoader classLoader) {
        this(new CamelCaseToUnderscoreConverter(), classLoader);
    }

    public ScenarioFileLoader(ClassToFilenameConverter converter, ClassLoader classLoader) {
        this.converter = converter;
        this.classLoader = classLoader;
    }

    public InputStream loadScenarioFor(Class<? extends Scenario> scenarioClass) {
        String scenarioFilename = converter.convert(scenarioClass);
        return classLoader.getResourceAsStream(scenarioFilename);
    }

}
