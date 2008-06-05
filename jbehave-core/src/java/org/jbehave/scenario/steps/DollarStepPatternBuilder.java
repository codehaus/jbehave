package org.jbehave.scenario.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DollarStepPatternBuilder implements StepPatternBuilder {

	public Pattern buildPattern(String matchThis) {
		String escapedMatch = escapeBrackets(matchThis);
		Matcher replacingArgsWithCapture = Pattern.compile("(\\$\\w*)(\\W|\\Z)", Pattern.DOTALL).matcher(escapedMatch);
		List<Replacement> replacements = new ArrayList<Replacement>();
		while(replacingArgsWithCapture.find()) {
			replacements.add(new Replacement(replacingArgsWithCapture.start(), replacingArgsWithCapture.end(), replacingArgsWithCapture.group(2)));
		}
		String matchTemp = escapedMatch;
		for (int i = replacements.size(); i > 0; i--) {
			String start = matchTemp.substring(0, replacements.get(i - 1).start);
			String end = matchTemp.substring(replacements.get(i - 1).end);
			String whitespaceIfAny = replacements.get(i - 1).whitespaceIfAny;
			matchTemp = start + "(.*)" + whitespaceIfAny + end;
		}
		return Pattern.compile(matchTemp, Pattern.DOTALL);
	}
	
	private String escapeBrackets(String matchThis) {
		String escapedMatch = matchThis.replace("(", "\\(");
		escapedMatch = escapedMatch.replace(")", "\\)");
		return escapedMatch;
	}

	private static class Replacement {
		private final int start;
		private final int end;
		private final String whitespaceIfAny;

		public Replacement(int start, int end, String whitespaceIfAny) {
			this.start = start;
			this.end = end;
			this.whitespaceIfAny = whitespaceIfAny;
		}
	}

}
