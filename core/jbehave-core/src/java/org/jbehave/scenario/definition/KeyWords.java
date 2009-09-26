package org.jbehave.scenario.definition;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Map;

import org.jbehave.scenario.i18n.StringEncoder;

/**
 * Provides the keywords which allow parsers to find steps in scenarios and
 * match those steps with candidates through the Given, When and Then
 * annotations
 */
public class KeyWords {

	static final String SCENARIO = "Scenario";
	static final String GIVEN_SCENARIOS = "GivenScenarios";
	static final String EXAMPLES_TABLE = "ExamplesTable";
	static final String GIVEN = "Given";
	static final String WHEN = "When";
	static final String THEN = "Then";
	static final String AND = "And";
	static final String PENDING = "Pending";
	static final String NOT_PERFORMED = "NotPerformed";
	static final String FAILED = "Failed";
	static final String EXAMPLES_TABLE_ROW = "ExamplesTableRow";
	protected static final List<String> KEYWORDS = asList(SCENARIO,
			GIVEN_SCENARIOS, EXAMPLES_TABLE, GIVEN, WHEN, THEN, AND, PENDING,
			NOT_PERFORMED, FAILED, EXAMPLES_TABLE_ROW);

	private final String scenario;
	private final String givenScenarios;
	private final String given;
	private final String when;
	private final String then;
	private final String examplesTable;
	private final String[] others;
	private StringEncoder encoder;

	public KeyWords(Map<String, String> keywords, StringEncoder encoder) {
		this(keywords.get(SCENARIO), keywords.get(GIVEN_SCENARIOS), keywords
				.get(EXAMPLES_TABLE), keywords.get(GIVEN), keywords.get(WHEN),
				keywords.get(THEN), keywords.get(AND), keywords.get(PENDING),
				keywords.get(NOT_PERFORMED), keywords.get(FAILED), keywords.get(EXAMPLES_TABLE_ROW));
		this.encoder = encoder;
	}

	public KeyWords(String scenario, String givenScenarios,
			String examplesTable, String given, String when, String then,
			String... others) {
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

	public String pending() {
		return others[1];
	}

	public String notPerformed() {
		return others[2];
	}

	public String failed() {
		return others[3];
	}

	public String examplesTableRow() {
		return others[4];
	}

	public String[] others() {
		return others;
	}

	public String encode(String value) {
		if ( encoder != null ){
			return encoder.encode(value);
		}
		return value;
	}

}
