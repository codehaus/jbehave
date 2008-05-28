package org.jbehave.scenario.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCaseToUnderscoreConverter implements ClassToFilenameConverter {

	public String convert(Class<?> scenarioClass) {

		String packaje = scenarioClass.getPackage().getName();
		String packajedir = packaje.replaceAll("\\.", "/");
		
		Matcher matcher = Pattern.compile("([A-Z].*?)([A-Z]|\\z)").matcher(scenarioClass.getSimpleName());
		int startAt = 0;
		StringBuilder builder = new StringBuilder();
		while(matcher.find(startAt)) {
			builder.append(matcher.group(1).toLowerCase());
			builder.append("_");
			startAt = matcher.start(2);
		}
		
		String underscored = builder.substring(0, builder.length() - 1);
		return packajedir + "/" + underscored;
	}

}
