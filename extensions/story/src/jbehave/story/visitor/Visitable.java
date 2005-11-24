package jbehave.story.visitor;

public interface Visitable {
	void accept(Visitor visitor);
}
