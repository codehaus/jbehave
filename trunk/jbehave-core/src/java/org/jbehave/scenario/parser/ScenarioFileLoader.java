package org.jbehave.scenario.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.StoryDefinition;
import org.jbehave.scenario.errors.InvalidScenarioResourceException;
import org.jbehave.scenario.errors.ScenarioNotFoundException;

public class ScenarioFileLoader implements ScenarioDefiner {
    private final ScenarioFileNameResolver resolver;
    private final ClassLoader classLoader;
    private final ScenarioParser stepParser;

    public ScenarioFileLoader() {
        this(new UnderscoredCamelCaseResolver(), Thread.currentThread().getContextClassLoader(), new PatternScenarioParser(new PropertyBasedConfiguration()));
    }

    public ScenarioFileLoader(ScenarioParser stepParser) {
        this(new UnderscoredCamelCaseResolver(), Thread.currentThread().getContextClassLoader(), stepParser);
    }

    public ScenarioFileLoader(ScenarioFileNameResolver converter, ScenarioParser parser) {
        this(converter, Thread.currentThread().getContextClassLoader(), parser);
    }

    public ScenarioFileLoader(ScenarioFileNameResolver resolver, ClassLoader classLoader, ScenarioParser stepParser) {
        this.resolver = resolver;
        this.classLoader = classLoader;
        this.stepParser = stepParser;
    }

    private InputStream loadInputStreamFor(Class<? extends Scenario> scenarioClass) {
        String scenarioFileName = resolver.resolve(scenarioClass);
        InputStream stream = classLoader.getResourceAsStream(scenarioFileName);
        if ( stream == null ){
            throw new ScenarioNotFoundException("Scenario file "+scenarioFileName+" could not be found by classloader "+classLoader);
        }
        return stream;
    }

    public StoryDefinition loadScenarioDefinitionsFor(Class<? extends Scenario> scenarioClass) {
        String wholeFileAsString = asString(loadInputStreamFor(scenarioClass));
        return stepParser.defineStoryFrom(wholeFileAsString);
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
