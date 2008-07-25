package org.jbehave.scenario.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jbehave.scenario.Scenario;

public class ScenarioFileLoader implements ScenarioDefiner {

    private final ScenarioFileNameResolver resolver;
    private final ClassLoader classLoader;

    public ScenarioFileLoader() {
        this(new UnderscoredCamelCaseResolver(), Thread.currentThread().getContextClassLoader());
    }

    public ScenarioFileLoader(ScenarioFileNameResolver converter) {
        this(converter, Thread.currentThread().getContextClassLoader());
    }

    public ScenarioFileLoader(ClassLoader classLoader) {
        this(new UnderscoredCamelCaseResolver(), classLoader);
    }

    public ScenarioFileLoader(ScenarioFileNameResolver resolver, ClassLoader classLoader) {
        this.resolver = resolver;
        this.classLoader = classLoader;
    }

    private InputStream loadStepsAsStreamFor(Class<? extends Scenario> scenarioClass) {
        String scenarioFileName = resolver.resolve(scenarioClass);
        InputStream stream = classLoader.getResourceAsStream(scenarioFileName);
        if ( stream == null ){
            throw new ScenarioNotFoundException("Scenario file "+scenarioFileName+" could not be found by classloader "+classLoader);
        }
        return stream;
    }

    public String loadStepsFor(Class<? extends Scenario> scenarioClass) {
        return asString(loadStepsAsStreamFor(scenarioClass));
    }

    private String asString(InputStream stream) {
        try {            
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write(bytes);
            return output.toString();
        } catch (IOException e) {
            throw new InvalidScenarioResourceException("Failed to convert scenario resouce to string", e);
        }
    }

}
