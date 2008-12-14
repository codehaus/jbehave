package org.jbehave.scenario.parser;

import org.jbehave.scenario.RunnableScenario;

public abstract class AbstractScenarioNameResolver implements ScenarioNameResolver {

	static final String DOT_REGEX = "\\.";
    static final String SLASH = "/";
	static final String EMPTY = "";

    protected String toPackageDir(Class<? extends RunnableScenario> scenarioClass) {
		Package scenarioPackage = scenarioClass.getPackage();
		if ( scenarioPackage != null ){
			return scenarioPackage.getName().replaceAll(DOT_REGEX, SLASH);			
		}
		return EMPTY;
	}

}