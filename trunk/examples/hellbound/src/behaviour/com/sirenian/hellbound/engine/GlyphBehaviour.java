package com.sirenian.hellbound.engine;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Constraint;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.engine.Glyph;

public class GlyphBehaviour extends UsingMiniMock {
	
	public void shouldNotifyListenersOfMovement() throws Exception {
			
		Segment[] segments1 = new Segment[] {new Segment(0, 0)};
		Segment[] segments2 = new Segment[] {new Segment(0, 1)};
		
		Glyph glyph = new Glyph(GlyphType.O, GlyphType.O.rotationsAtRoot[0]);
		
		Mock listener1 = mock(GlyphListener.class);
		Mock listener2 = mock(GlyphListener.class);

		expectGlyphMovement(listener1, GlyphType.O.rotationsAtRoot[0], GlyphType.O.rotationsAtRoot[0]);
		expectGlyphMovement(listener2, GlyphType.O.rotationsAtRoot[0], GlyphType.O.rotationsAtRoot[0]);
		expectGlyphMovement(listener1, GlyphType.O.rotationsAtRoot[0], segments1);		
		expectGlyphMovement(listener2, GlyphType.O.rotationsAtRoot[0], segments1);		
		expectGlyphMovement(listener1, segments1, segments2);			
		expectGlyphMovement(listener2, segments1, segments2);		
		
		glyph.addListener((GlyphListener) listener1);
		glyph.addListener((GlyphListener) listener2);
		
		glyph.moveTo(segments1);
		glyph.moveTo(segments2);
		
		verifyMocks();
	}


	private void expectGlyphMovement(Mock listener, Segment[] origin, Segment[] destination) {
		listener.expects("reportGlyphMovement").with(new Constraint[] {
				eq(GlyphType.O),
				new ArrayConstraint(origin),
				new ArrayConstraint(destination)
		});
	}
	
	
	private class ArrayConstraint implements Constraint {
		private final Object[] expected;

		public ArrayConstraint(Object[] expected) {
			this.expected = expected;
		}
		
		public boolean matches(Object actual) {
			if (!(actual instanceof Object[])) return false;
			Object[] actualArray = (Object[]) actual;	
			
			if (actualArray.length != expected.length) return false;
			
			for (int i = 0; i < actualArray.length; i++) {
				if (!actualArray[i].equals(expected[i])) {
					return false;
				}
			}
			return true;
		}
	}
}
