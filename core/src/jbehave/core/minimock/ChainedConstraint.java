package jbehave.core.minimock;

public abstract class ChainedConstraint extends UsingConstraints implements Constraint {
	/** forces subclasses to implement <tt>toString</tt> */
	public abstract String toString();
	
	public ChainedConstraint and(Constraint that) {
		return and(this, that);
	}
	public ChainedConstraint or(Constraint that) {
		return or(this, that);
	}
}
