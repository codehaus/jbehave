package org.jbehave.scenario.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.scenario.Scenario;

public class UnderscoredCamelCaseResolver implements ScenarioFileNameResolver {

	private static final String UNDERSCORE = "_";
	private final String extension;

    public UnderscoredCamelCaseResolver() {
        this("");
    }

    public UnderscoredCamelCaseResolver(String extension) {
        this.extension = extension;
    }

    public String resolve(Class<? extends Scenario> scenarioClass) {

		String packageDir = scenarioClass.getPackage().getName().replaceAll("\\.", "/");
		
		Matcher matcher = Pattern.compile("([A-Z].*?)([A-Z]|\\z)").matcher(scenarioClass.getSimpleName());
		int startAt = 0;
		StringBuilder builder = new StringBuilder();
		while(matcher.find(startAt)) {
			builder.append(matcher.group(1).toLowerCase());
			builder.append(UNDERSCORE);
			startAt = matcher.start(2);
		}
		
		String underscoredName = builder.substring(0, builder.length() - 1);
		return packageDir + "/" + underscoredName + extension;
	}

}
