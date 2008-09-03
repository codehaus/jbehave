package org.jbehave.scenario.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.errors.InvalidScenarioResourceException;
import org.jbehave.scenario.errors.ScenarioNotFoundException;

public class ScenarioFileLoader implements ScenarioDefiner {
    
    private final ScenarioFileNameResolver resolver;
    private final ScenarioParser parser;
    private final ClassLoader classLoader;

    public ScenarioFileLoader() {
        this(new UnderscoredCamelCaseResolver(), new PatternScenarioParser(), Thread.currentThread().getContextClassLoader());
    }

    public ScenarioFileLoader(ScenarioParser parser) {
        this(new UnderscoredCamelCaseResolver(), parser, Thread.currentThread().getContextClassLoader());
    }

    public ScenarioFileLoader(ScenarioFileNameResolver converter, ScenarioParser parser) {
        this(converter, parser, Thread.currentThread().getContextClassLoader());
    }

    public ScenarioFileLoader(ScenarioFileNameResolver converter, ClassLoader classLoader) {
        this(converter, new PatternScenarioParser(), classLoader);
    }

    public ScenarioFileLoader(ScenarioFileNameResolver resolver, ScenarioParser parser, ClassLoader classLoader) {
        this.resolver = resolver;
        this.parser = parser;
        this.classLoader = classLoader;
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
        return parser.defineStoryFrom(wholeFileAsString);
    }
  
    private String asString(InputStream stream) {
        try {            
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write(bytes);
            return output.toString();
        } catch (IOException e) {
            throw new InvalidScenarioResourceException("Failed to convert scenario resource to string", e);
        }
    }
}
