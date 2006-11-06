package jbehave.core.story.visitor;

import java.util.Arrays;
import java.util.Iterator;

import jbehave.core.UsingMocks;


public class CompositeVisitableUsingMiniMock {
	protected final VisitableArrayList visitables = new VisitableArrayList();

	public CompositeVisitableUsingMiniMock(Visitable[] visitables) {
		this.visitables.addAll(Arrays.asList(visitables));
	}

	public boolean containsMocks() {
		for (Iterator i = visitables.iterator(); i.hasNext();) {
			if (((UsingMocks) i.next()).containsMocks()) {
				return true;
			}
		}
		return false;
	}

	public void verifyMocks() {
		for (Iterator i = visitables.iterator(); i.hasNext();) {
			((UsingMocks) i.next()).verifyMocks();
		}
	}
    
    public void accept(Visitor visitor) {
        visitables.accept(visitor);
    }

}
