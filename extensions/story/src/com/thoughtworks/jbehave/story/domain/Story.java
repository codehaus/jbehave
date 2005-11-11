package com.thoughtworks.jbehave.story.domain;

import com.thoughtworks.jbehave.story.visitor.Visitor;

public interface Story {

	String title();

	Narrative narrative();

	void accept(Visitor visitor);

}