package org.jbehave.scenario.definition;

/**
 * Provides the keywords which allow parsers to find steps in scenarios and
 * match those steps with candidates through the Given, When and Then
 * annotations
 */
public class KeyWords {

	private final String scenario;
	private final String given;
	private final String when;
	private final String then;
	private final String table;
	private final String[] others;

	public KeyWords(String scenario, String given, String when, String then,
			String table, String... others) {
		this.scenario = scenario;
		this.given = given;
		this.when = when;
		this.then = then;
		this.table = table;
		this.others = others;
	}

	public String scenario() {
		return scenario;
	}

	public String table() {
		return table;
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

	public String[] others() {
		return others;
	}

}
