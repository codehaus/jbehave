package org.jbehave.scenario.fromtext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StepParser {

	public String[] findSteps(String scenarioAsString) {
		Matcher matcher = Pattern.compile("((Given|When|Then) (.|\\s)*?)\\s*(\\Z|Given|When|Then)").matcher(scenarioAsString);
		List<String> steps = new ArrayList<String>();
		int startAt = 0;
		while(matcher.find(startAt)) {
			steps.add(matcher.group(1));
			startAt = matcher.start(4);
		}
		
		return steps.toArray(new String[steps.size()]);
	}

}
