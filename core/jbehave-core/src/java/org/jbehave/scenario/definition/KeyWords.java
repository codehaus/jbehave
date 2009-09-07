package org.jbehave.scenario.definition;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Provides the keywords which allow parsers to find steps in scenarios and
 * match those steps with candidates through the Given, When and Then
 * annotations
 */
public class KeyWords {

	static final String SCENARIO = "Scenario";
	static final String GIVEN_SCENARIOS = "GivenScenarios";
	static final String GIVEN = "Given";
	static final String WHEN = "When";
	static final String THEN = "Then";
	static final String AND = "And";
	static final String EXAMPLES_TABLE = "ExamplesTable";
	protected static final List<String> KEYWORDS = Arrays.asList(SCENARIO, GIVEN_SCENARIOS, GIVEN, WHEN, THEN, AND, EXAMPLES_TABLE);
	
	private final String scenario;
	private final String givenScenarios;
	private final String given;
	private final String when;
	private final String then;
	private final String examplesTable;
	private final String[] others;

	public KeyWords(Map<String, String> keywords) {
		this(keywords.get(SCENARIO), keywords.get(GIVEN_SCENARIOS), keywords
		.get(EXAMPLES_TABLE), keywords.get(GIVEN), keywords.get(WHEN), keywords.get(THEN), keywords.get(AND));
	}

	public KeyWords(String scenario, String givenScenarios, String examplesTable,
			String given, String when, String then, String... others) {
		this.scenario = scenario;
		this.givenScenarios = givenScenarios;
		this.examplesTable = examplesTable;
		this.given = given;
		this.when = when;
		this.then = then;
		this.others = others;
	}

	public String scenario() {
		return scenario;
	}

	public String givenScenarios() {
		return givenScenarios;
	}

	public String examplesTable() {
		return examplesTable;
	}

	public String given() {
		return given;
	}

	public String when() {
		return when;
	}

	public String then() {
		return then;
	}

	public String and() {
		return others[0];
	}

	public String[] others() {
		return others;
	}

}
