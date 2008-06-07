package org.jbehave.scenario.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        return classLoader.getResourceAsStream(converter.convert(scenarioClass));
    }

    public String loadScenarioAsString(Class<? extends Scenario> scenarioClass) {
        return asString(loadScenarioFor(scenarioClass));
    }

    private String asString(InputStream stream) {
        try {
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write(bytes);
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
