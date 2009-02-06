package org.jbehave.web.waffle.controllers;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ScenarioContext {

	private static final String EMPTY = "";
	private static final List<String> EMPTY_LIST = asList();

	private String input;
	private String output;
	private List<String> messages;

	public ScenarioContext() {
		this(EMPTY, EMPTY, EMPTY_LIST);
	}

	public ScenarioContext(String input, String output, List<String> messages) {
		this.input = input;
		this.output = output;
		this.messages = new ArrayList<String>();
		this.messages.addAll(messages);
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

	public void addMessage(String message) {
		this.messages.add(message);
	}	

	public List<String> getMessages() {
		return messages;
	}

	public void clearMessages() {
		this.messages.clear();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	

}
