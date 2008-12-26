package org.jbehave.scenario.parser;

import static java.text.MessageFormat.format;

import org.jbehave.scenario.RunnableScenario;

public abstract class AbstractScenarioNameResolver implements
		ScenarioNameResolver {

	static final String DOT_REGEX = "\\.";
	static final String SLASH = "/";
	static final String EMPTY = "";
	static final String DEFAULT_EXTENSION = "";
	static final String PATH_PATTERN = "{0}/{1}{2}";

	private final String extension;

	protected AbstractScenarioNameResolver() {
		this(DEFAULT_EXTENSION);
	}

	protected AbstractScenarioNameResolver(String extension) {
		this.extension = extension;
	}

	public String resolve(Class<? extends RunnableScenario> scenarioClass) {
		return format(PATH_PATTERN, resolveDirectoryName(scenarioClass), resolveFileName(scenarioClass), extension);
	}

	protected String resolveDirectoryName(
			Class<? extends RunnableScenario> scenarioClass) {
		Package scenarioPackage = scenarioClass.getPackage();
		if (scenarioPackage != null) {
			return scenarioPackage.getName().replaceAll(DOT_REGEX, SLASH);
		}
		return EMPTY;
	}

	protected abstract String resolveFileName(Class<? extends RunnableScenario> scenarioClass);

}