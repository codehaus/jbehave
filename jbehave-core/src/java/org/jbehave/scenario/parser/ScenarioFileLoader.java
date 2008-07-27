package org.jbehave.scenario.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jbehave.scenario.Scenario;

public class ScenarioFileLoader implements ScenarioDefiner {

    private final ScenarioFileNameResolver resolver;
    private final ClassLoader classLoader;
	private final StepParser stepParser;

    public ScenarioFileLoader() {
        this(new UnderscoredCamelCaseResolver(), Thread.currentThread().getContextClassLoader(), new PatternStepParser());
    }

    public ScenarioFileLoader(StepParser stepParser) {
		this(new UnderscoredCamelCaseResolver(), Thread.currentThread().getContextClassLoader(), stepParser);
	}

    public ScenarioFileLoader(ScenarioFileNameResolver converter, StepParser parser) {
        this(converter, Thread.currentThread().getContextClassLoader(), parser);
    }

    public ScenarioFileLoader(ClassLoader classLoader) {
        this(new UnderscoredCamelCaseResolver(), classLoader, new PatternStepParser());
    }

    public ScenarioFileLoader(ScenarioFileNameResolver resolver, ClassLoader classLoader, StepParser stepParser) {
        this.resolver = resolver;
        this.classLoader = classLoader;
		this.stepParser = stepParser;
    }

	private InputStream loadStepsAsStreamFor(Class<? extends Scenario> scenarioClass) {
        String scenarioFileName = resolver.resolve(scenarioClass);
        InputStream stream = classLoader.getResourceAsStream(scenarioFileName);
        if ( stream == null ){
            throw new ScenarioNotFoundException("Scenario file "+scenarioFileName+" could not be found by classloader "+classLoader);
        }
        return stream;
    }

    public ScenarioDefinition loadStepsFor(Class<? extends Scenario> scenarioClass) {
        return new ScenarioDefinition(stepParser, asString(loadStepsAsStreamFor(scenarioClass)));
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
