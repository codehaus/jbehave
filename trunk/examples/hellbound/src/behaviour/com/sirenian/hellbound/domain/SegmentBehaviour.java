package com.sirenian.hellbound.domain;

import jbehave.core.mock.UsingMatchers;

public class SegmentBehaviour extends UsingMatchers {
	
	public void shouldBeEqualToSegmentAtSameCoordinates() {
		Segment segment1 = new Segment(1, 3);
		Segment segment2 = new Segment(1, 3);
		Segment segment3 = new Segment(4, 2);
		
		ensureThat(segment1, eq(segment2));
		ensureThat(segment2, eq(segment1));
		ensureThat(segment3, not(eq(segment1)));
		
		ensureThat(segment1.hashCode(), eq(segment2.hashCode()));
	}
    
    public void shouldMoveRightOnePlace() {
        ensureThat(new Segment(3, 2).movedRight(), eq(new Segment(4, 2)));
    }
    
    public void shouldMoveRightByOffset() {
        ensureThat(new Segment(3, 2).movedRight(3), eq(new Segment(6, 2)));
    }
    
    public void shouldMoveLeftOnePlace() {
        ensureThat(new Segment(3, 2).movedLeft(), eq(new Segment(2, 2)));
    }
    
    public void shouldMoveDownOnePlace() {
        ensureThat(new Segment(3, 2).movedDown(), eq(new Segment(3, 3)));
    }
    
    public void shouldMoveDownByOffset() {
        ensureThat(new Segment(3, 2).movedDown(3), eq(new Segment(3, 5)));
    }

}
