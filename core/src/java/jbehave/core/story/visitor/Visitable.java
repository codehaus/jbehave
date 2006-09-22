package jbehave.core.story.visitor;

public interface Visitable {
	void accept(Visitor visitor);
}
