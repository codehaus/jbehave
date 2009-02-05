package org.jbehave.web.waffle.controllers;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ScenarioData {

	private static final String EMPTY = "";

	private String input;
	private String output;

	public ScenarioData() {
		this(EMPTY, EMPTY);
	}

	public ScenarioData(String input, String output) {
		this.input = input;
		this.output = output;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
