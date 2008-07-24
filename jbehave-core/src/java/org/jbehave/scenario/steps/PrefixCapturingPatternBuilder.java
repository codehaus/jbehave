package org.jbehave.scenario.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides a pattern which will capture arguments starting with the given
 * prefix in any matching step. Default prefix is $.
 * 
 * @author Elizabeth Keogh
 */
public class PrefixCapturingPatternBuilder implements StepPatternBuilder {

	private String anyWordBeginningWithThePrefix = "(\\$\\w*)(\\W|\\Z)";

	/**
	 * Creates a pattern which captures arguments starting with $ in
	 * a matching step.
	 */
	public PrefixCapturingPatternBuilder() {
		this("$");
	}

	/**
	 * Creates a pattern which captures arguments starting with a given
	 * prefix in a matching step.
	 */
	public PrefixCapturingPatternBuilder(String prefix) {
		anyWordBeginningWithThePrefix = "(\\" + prefix + "\\w*)(\\W|\\Z)";
	}

	public Pattern buildPattern(String matchThis) {
		String matchThisButLeaveBrackets = escapeRegexpPunctuation(matchThis);
		List<Replacement> replacements = findArgumentsToReplace(matchThisButLeaveBrackets);
		String patternToMatchAgainst = replaceIdentifiedArgsWithCapture(matchThisButLeaveBrackets, replacements);
		String matchThisButIgnoreWhitespace = anyWhitespaceWillDo(patternToMatchAgainst);
		return Pattern.compile(matchThisButIgnoreWhitespace, Pattern.DOTALL);
	}

	private String anyWhitespaceWillDo(String matchThis) {
		return matchThis.replaceAll("\\s+", "\\\\s+");
	}

	private List<Replacement> findArgumentsToReplace(
			String matchThisButLeaveBrackets) {
		Matcher findingAllTheDollarWords = Pattern.compile(anyWordBeginningWithThePrefix, Pattern.DOTALL).matcher(matchThisButLeaveBrackets);
		List<Replacement> replacements = new ArrayList<Replacement>();
		while(findingAllTheDollarWords.find()) {
			replacements.add(new Replacement(findingAllTheDollarWords.start(), findingAllTheDollarWords.end(), findingAllTheDollarWords.group(2)));
		}
		return replacements;
	}

	private String replaceIdentifiedArgsWithCapture(String escapedMatch,
			List<Replacement> replacements) {
		String matchTemp = escapedMatch;
		for (int i = replacements.size(); i > 0; i--) {
			String start = matchTemp.substring(0, replacements.get(i - 1).start);
			String end = matchTemp.substring(replacements.get(i - 1).end);
			String whitespaceIfAny = replacements.get(i - 1).whitespaceIfAny;
			matchTemp = start + "(.*)" + whitespaceIfAny + end;
		}
		return matchTemp;
	}
	
	private String escapeRegexpPunctuation(String matchThis) {
		String escapedMatch = matchThis.replaceAll("([\\[\\]\\{\\}\\?\\^\\.\\*\\(\\)\\+\\\\])", "\\\\$1");
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
