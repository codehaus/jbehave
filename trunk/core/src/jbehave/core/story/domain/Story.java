package jbehave.core.story.domain;

import jbehave.core.story.visitor.Visitor;

public interface Story {

	String title();

	Narrative narrative();

	void accept(Visitor visitor);

}