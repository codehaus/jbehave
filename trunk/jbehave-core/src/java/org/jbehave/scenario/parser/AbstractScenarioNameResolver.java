package org.jbehave.scenario.parser;

import org.jbehave.scenario.RunnableScenario;

public abstract class AbstractScenarioNameResolver implements ScenarioNameResolver {

	static final String DOT_REGEX = "\\.";
    static final String SLASH = "/";

    protected String toPackageDir(Class<? extends RunnableScenario> scenarioClass) {
		return scenarioClass.getPackage().getName().replaceAll(DOT_REGEX, SLASH);
	}

}