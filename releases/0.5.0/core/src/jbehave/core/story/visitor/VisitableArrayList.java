package jbehave.core.story.visitor;

import java.util.ArrayList;
import java.util.Iterator;

public class VisitableArrayList extends ArrayList implements Visitable {
	
	public void accept(Visitor visitor) {
		for (Iterator i = iterator(); i.hasNext();) {
			((Visitable)i.next()).accept(visitor);
		}
	}
}
