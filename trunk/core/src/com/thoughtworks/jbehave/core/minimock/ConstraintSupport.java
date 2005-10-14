package com.thoughtworks.jbehave.core.minimock;

public abstract class ConstraintSupport extends UsingConstraints implements Constraint {
	/** Requires subclasses to implement <tt>toString</tt> */
	public abstract String toString();
	public ConstraintSupport and(Constraint that) {
		return and(this, that);
	}
	public ConstraintSupport or(Constraint that) {
		return or(this, that);
	}
}
