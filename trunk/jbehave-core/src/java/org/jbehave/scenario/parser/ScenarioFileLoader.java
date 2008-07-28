package org.jbehave.scenario.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public List<ScenarioDefinition>loadStepsFor(Class<? extends Scenario> scenarioClass) {
    	List<String> scenarios = asString(loadStepsAsStreamFor(scenarioClass));
    	List<ScenarioDefinition> scenarioDefinitions = new ArrayList<ScenarioDefinition>();
    	for (String string : scenarios) {
			scenarioDefinitions.add(new ScenarioDefinition(stepParser, string));
		}
        return scenarioDefinitions;
    }

    private List<String> asString(InputStream stream) {
        try {            
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            output.write(bytes);
            String allScenariosInFile = output.toString();
    		return splitScenarios(allScenariosInFile);
        } catch (IOException e) {
            throw new InvalidScenarioResourceException("Failed to convert scenario resouce to string", e);
        }
    }

	private List<String> splitScenarios(String allScenariosInFile) {
    	Pattern scenarioSplitter = Pattern.compile("((Scenario:) (.|\\s)*?)\\s*(\\Z|Scenario:).*", Pattern.DOTALL);
    	Matcher matcher = scenarioSplitter.matcher(allScenariosInFile);
    	int startAt = 0;
    	List<String> scenarios = new ArrayList<String>();
    	if (matcher.matches()) {
			while(matcher.find(startAt)) {
				scenarios.add(matcher.group(1));
				startAt = matcher.start(4);
			}
    	} else {
    		String loneScenario = allScenariosInFile;
			scenarios.add(loneScenario);
    	}
    	return scenarios;
	}

}
