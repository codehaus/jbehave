package jbehave.story.domain;

import jbehave.story.visitor.Visitor;

public interface Story {

	String title();

	Narrative narrative();

	void accept(Visitor visitor);

}