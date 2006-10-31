package com.sirenian.hellbound.domain;

import jbehave.core.mock.UsingConstraints;

public class SegmentBehaviour extends UsingConstraints {
	
	public void shouldBeEqualToSegmentAtSameCoordinates() {
		Segment segment1 = new Segment(1, 3);
		Segment segment2 = new Segment(1, 3);
		Segment segment3 = new Segment(4, 2);
		
		ensureThat(segment1, eq(segment2));
		ensureThat(segment2, eq(segment1));
		ensureThat(segment3, not(eq(segment1)));
		
		ensureThat(segment1.hashCode(), eq(segment2.hashCode()));
	}

}
