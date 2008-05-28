package org.jbehave.scenario.parser;

import java.io.InputStream;

import org.jbehave.scenario.Scenario;

public class FileFinder {
	
	private final ClassToFilenameConverter converter;
	
	public FileFinder() {
		this(new CamelCaseToUnderscoreConverter());
	}

	public FileFinder(ClassToFilenameConverter converter) {
		this.converter = converter;
	}

	public InputStream findFileMatching(Class<? extends Scenario> scenarioClass) {
		String scenarioFilename = converter.convert(scenarioClass);
		return scenarioClass.getClassLoader().getResourceAsStream(scenarioFilename);
	}

}
