package org.jbehave.scenario.parser;

import static java.util.regex.Pattern.DOTALL;
import static java.util.regex.Pattern.compile;
import static org.jbehave.scenario.definition.Blurb.EMPTY;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbehave.scenario.Configuration;
import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.definition.Blurb;
import org.jbehave.scenario.definition.KeyWords;
import org.jbehave.scenario.definition.ScenarioDefinition;
import org.jbehave.scenario.definition.StoryDefinition;
import org.jbehave.scenario.definition.Table;

/**
 * Pattern-based scenario parser, which uses the configured keywords to find the
 * steps in the text scenarios.
 */
public class PatternScenarioParser implements ScenarioParser {

	private final Configuration configuration;

	public PatternScenarioParser() {
		this(new PropertyBasedConfiguration());
	}

	public PatternScenarioParser(Configuration configuration) {
		this.configuration = configuration;
	}

	public StoryDefinition defineStoryFrom(String wholeStoryAsString) {
		Blurb blurb = parseBlurbFrom(wholeStoryAsString);
		List<ScenarioDefinition> scenarioDefinitions = parseScenariosFrom(wholeStoryAsString);
		return new StoryDefinition(blurb, scenarioDefinitions);
	}

	private List<ScenarioDefinition> parseScenariosFrom(
			String wholeStoryAsString) {
		List<ScenarioDefinition> scenarioDefinitions = new ArrayList<ScenarioDefinition>();
		List<String> scenarios = splitScenarios(wholeStoryAsString);
		for (String scenario : scenarios) {
			String title = findTitle(scenario);
			Table table = findTable(scenario);
			List<String> givenScenarios = findGivenScenarios(scenario);
			List<String> steps = findSteps(scenario);
			scenarioDefinitions
					.add(new ScenarioDefinition(title, givenScenarios, table, steps));
		}
		return scenarioDefinitions;
	}

	private String findTitle(String scenario) {
		Matcher findingTitle = patternToPullScenarioTitleIntoGroupOne()
				.matcher(scenario);
		return findingTitle.find() ? findingTitle.group(1).trim() : "";
	}

	private Table findTable(String scenario) {
		Matcher findingTable = patternToPullScenarioTableIntoGroupOne()
		.matcher(scenario);
		String table = findingTable.find() ? findingTable.group(1).trim() : "";
		return new Table(table);
	}

	private List<String> findGivenScenarios(String scenario) {
		Matcher findingGivenScenarios = patternToPullGivenScenariosIntoGroupOne()
		.matcher(scenario);
		String givenScenariosAsCSV = findingGivenScenarios.find() ? findingGivenScenarios.group(1).trim() : "";
		List<String> givenScenarios = new ArrayList<String>();		
		for ( String givenScenario : givenScenariosAsCSV.split(",") ){			
			String trimmed = givenScenario.trim();
			if ( trimmed.length() > 0 ) {
				givenScenarios.add(trimmed);
			}
		}
		return givenScenarios;
	}

	private List<String> findSteps(String scenarioAsString) {
		Matcher matcher = patternToPullOutSteps().matcher(scenarioAsString);
		List<String> steps = new ArrayList<String>();
		int startAt = 0;
		while (matcher.find(startAt)) {
			steps.add(matcher.group(1));
			startAt = matcher.start(4);
		}
		return steps;
	}

	private Blurb parseBlurbFrom(String wholeStoryAsString) {
		String scenario = configuration.keywords().scenario();
		Pattern findStoryBlurb = compile("(.*?)(" + scenario + ").*", DOTALL);
		Matcher matcher = findStoryBlurb.matcher(wholeStoryAsString);
		if (matcher.find()) {
			return new Blurb(matcher.group(1).trim());
		} else {
			return EMPTY;
		}
	}

	@SuppressWarnings("serial")
	public static class InvalidPatternException extends RuntimeException {
		public InvalidPatternException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	protected List<String> splitScenarios(String allScenariosInFile) {
		return splitScenariosWithKeyword(allScenariosInFile);
	}

	protected List<String> splitScenariosWithKeyword(String allScenariosInFile) {
		List<String> scenarios = new ArrayList<String>();
		String scenarioKeyword = "Scenario".replace("Scenario", configuration
				.keywords().scenario());

		String allScenarios = null;
		// chomp off anything before first keyword, if found
		int keywordIndex = allScenariosInFile.indexOf(scenarioKeyword);
		if (keywordIndex != -1) {
			allScenarios = allScenariosInFile.substring(keywordIndex);
		} else { // use all scenarios in file
			allScenarios = allScenariosInFile;
		}

		for (String scenario : allScenarios.split(scenarioKeyword)) {
			if (scenario.trim().length() > 0) {
				scenarios.add(scenarioKeyword + scenario);
			}
		}
		return scenarios;
	}

	// This pattern approach causes stack overflow error on Windows
	// http://jbehave.org/documentation/known-issues/regex-stack-overflow-errors
	protected List<String> splitScenariosWithPattern(String allScenariosInFile) {
		Pattern scenarioSplitter = patternToPullScenariosIntoGroupFour();
		Matcher matcher = scenarioSplitter.matcher(allScenariosInFile);
		int startAt = 0;
		List<String> scenarios = new ArrayList<String>();
		try {
			if (matcher.matches()) {
				while (matcher.find(startAt)) {
					scenarios.add(matcher.group(1));
					startAt = matcher.start(4);
				}
			} else {
				String loneScenario = allScenariosInFile;
				scenarios.add(loneScenario);
			}
		} catch (StackOverflowError e) {
			// TODO - wish we had the scenario file name here.
			throw new InvalidPatternException(
					"Failed to parse scenarios (see http://jbehave.org/documentation/known-issues/regex-stack-overflow-errors): "
							+ allScenariosInFile, e);
		}
		return scenarios;
	}

	private Pattern patternToPullScenariosIntoGroupFour() {
		String scenario = configuration.keywords().scenario();
		return compile(".*?((" + scenario + ") (.|\\s)*?)\\s*(\\Z|" + scenario
				+ ").*", DOTALL);
	}

	private Pattern patternToPullGivenScenariosIntoGroupOne() {
		KeyWords keywords = configuration.keywords();
		String givenScenarios = keywords.givenScenarios();
		String concatenatedKeywords = concatenateWithOr(keywords.given(),
				keywords.when(), keywords.then(), keywords.others());
		return compile(".*"+givenScenarios+"(.*?)\\s*(" + concatenatedKeywords + ").*");
	}

	private Pattern patternToPullScenarioTableIntoGroupOne() {
		KeyWords keywords = configuration.keywords();
		String table = keywords.table();
		return compile(".*"+table+"\\s*((.|\\s)*)");
	}

	private Pattern patternToPullScenarioTitleIntoGroupOne() {
		KeyWords keywords = configuration.keywords();
		String scenario = keywords.scenario();
		String concatenatedKeywords = concatenateWithOr(keywords.given(),
				keywords.when(), keywords.then(), keywords.others());
		return compile(scenario + "(.*?)\\s*(" + concatenatedKeywords + ").*");
	}

	private String concatenateWithOr(String given, String when, String then,
			String[] others) {
		return concatenateWithOr(false, given, when, then, others);
	}

	private String concatenateWithSpaceOr(String given, String when,
			String then, String[] others) {
		return concatenateWithOr(true, given, when, then, others);
	}

	private String concatenateWithOr(boolean usingSpace, String given,
			String when, String then, String[] others) {
		StringBuilder builder = new StringBuilder();
		builder.append(given).append(usingSpace ? "\\s|" : "|");
		builder.append(when).append(usingSpace ? "\\s|" : "|");
		builder.append(then).append(usingSpace ? "\\s|" : "|");
		builder.append(usingSpace ? concatenateWithSpaceOr(others)
				: concatenateWithOr(others));
		return builder.toString();
	}

	private String concatenateWithOr(String... keywords) {
		return concatenateWithOr(false, new StringBuilder(), keywords);
	}

	private String concatenateWithSpaceOr(String... keywords) {
		return concatenateWithOr(true, new StringBuilder(), keywords);
	}

	private String concatenateWithOr(boolean usingSpace, StringBuilder builder,
			String[] keywords) {
		for (String other : keywords) {
			builder.append(other).append(usingSpace ? "\\s|" : "|");
		}
		String result = builder.toString();
		return result.substring(0, result.length() - 1); // chop off the last |
	}

	private Pattern patternToPullOutSteps() {
		KeyWords keywords = configuration.keywords();
		String givenWhenThen = concatenateWithOr(keywords.given(), keywords
				.when(), keywords.then(), keywords.others());
		String givenWhenThenSpaced = concatenateWithSpaceOr(keywords.given(),
				keywords.when(), keywords.then(), keywords.others());
		String scenario = keywords.scenario();
		String table = keywords.table();
		return compile("((" + givenWhenThen + ") (.|\\s)*?)\\s*(\\Z|"
				+ givenWhenThenSpaced + "|" + scenario + "|"+ table + ")");
	}
}
