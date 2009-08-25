package com.sirenian.hellbound.domain;
import org.jbehave.core.mock.UsingMatchers;

public class SegmentsBehaviour extends UsingMatchers {

	public void shouldMoveEachSegmentRight() {
		Segments segments = new Segments(new Segment[] {
				new Segment(0, 1),
				new Segment(0, 2),
				new Segment(0, 3)
		});
		
		Segments movedSegments = segments.movedRight();
		
		ensureThat(movedSegments.get(0), eq(new Segment(1, 1)));
		ensureThat(movedSegments.get(1), eq(new Segment(1, 2)));
		ensureThat(movedSegments.get(2), eq(new Segment(1, 3)));
	}
	
	public void shouldMoveEachSegmentRightByOffset() {
		Segments segments = new Segments(new Segment[] {
				new Segment(0, 1),
				new Segment(0, 2),
				new Segment(0, 3)
		});
		
		Segments movedSegments = segments.movedRight(3);
		
		ensureThat(movedSegments.get(0), eq(new Segment(3, 1)));
		ensureThat(movedSegments.get(1), eq(new Segment(3, 2)));
		ensureThat(movedSegments.get(2), eq(new Segment(3, 3)));
	}	
	
	public void shouldMoveEachSegmentLeft() {
		Segments segments = new Segments(new Segment[] {
				new Segment(1, 1),
				new Segment(1, 2),
				new Segment(1, 3)
		});
		
		Segments movedSegments = segments.movedLeft();
		
		ensureThat(movedSegments.get(0), eq(new Segment(0, 1)));
		ensureThat(movedSegments.get(1), eq(new Segment(0, 2)));
		ensureThat(movedSegments.get(2), eq(new Segment(0, 3)));
	}
	
	public void shouldMoveEachSegmentDown() {
		Segments segments = new Segments(new Segment[] {
				new Segment(0, 1),
				new Segment(0, 2),
				new Segment(0, 3)
		});
		
		Segments movedSegments = segments.movedDown();
		
		ensureThat(movedSegments.get(0), eq(new Segment(0, 2)));
		ensureThat(movedSegments.get(1), eq(new Segment(0, 3)));
		ensureThat(movedSegments.get(2), eq(new Segment(0, 4)));
	}
    
    public void shouldMoveEachSegmentDownByOffset() {
        Segments segments = new Segments(new Segment[] {
                new Segment(0, 1),
                new Segment(0, 2),
                new Segment(0, 3)
        });
        
        Segments movedSegments = segments.movedDown(4);
        
        ensureThat(movedSegments.get(0), eq(new Segment(0, 5)));
        ensureThat(movedSegments.get(1), eq(new Segment(0, 6)));
        ensureThat(movedSegments.get(2), eq(new Segment(0, 7)));
    }   
	
	public void shouldBeEqualToSegmentsWithSameCoordinatesAndOrder() {
		Segments segments1 = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3), 
				new Segment(0, 4));
		Segments segments2 = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3), 
				new Segment(0, 4));
		
		Segments segments3 = new Segments(
				new Segment(0, 4), 
				new Segment(0, 3), 
				new Segment(0, 2), 
				new Segment(0, 1));
		
		ensureThat(segments1, eq(segments2));
		ensureThat(segments2, eq(segments1));
		ensureThat(segments3, not(eq(segments1)));
		
		ensureThat(segments1.hashCode(), eq(segments2.hashCode()));
	}
	
	public void shouldHaveSizeEqualToNumberOfSegments() {
		Segments segments1 = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3), 
				new Segment(0, 4));
		Segments segments2 = new Segments(new Segment[] {
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3)});
		
		ensureThat(segments1.size(), eq(4));
		ensureThat(segments2.size(), eq(3));
	}
	
	public void shouldContainSegmentsWithWhichItWasConstructed() {
		Segments segments = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3), 
				new Segment(0, 4));
		
		ensureThat(segments.contains(new Segment(0, 1)));
		ensureThat(!segments.contains(new Segment(0, 5)));
	}
	
	public void shouldOverlapSegmentsWithAnyOfTheSameSegmentsAsSelf() {
		Segments segments1 = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3), 
				new Segment(0, 4));
		
		Segments segments2 = new Segments(
				new Segment(1, 1), 
				new Segment(2, 2), 
				new Segment(3, 3), 
				new Segment(4, 4));
		
		Segments segments3 = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(1, 3), 
				new Segment(1, 4));
		
		ensureThat(segments1.overlaps(segments3));
		ensureThat(segments3.overlaps(segments1));
		ensureThat(!segments1.overlaps(segments2));
	}
	
	public void shouldReturnTheLowestYPosition() {
		Segments segments = new Segments(
				new Segment(0, 1), 
				new Segment(0, 4), 
				new Segment(0, 3), 
				new Segment(0, 2));
		
		ensureThat(segments.lowest(), eq(4));
	}
    
    public void shouldReturnTheLeftmostXPosition() {
        Segments segments = new Segments(
                new Segment(4, 1), 
                new Segment(3, 4), 
                new Segment(2, 3), 
                new Segment(5, 2));
        
        ensureThat(segments.leftmost(), eq(2));
    }
    
    public void shouldReturnTheRightmostXPosition() {
        Segments segments = new Segments(
                new Segment(4, 1), 
                new Segment(3, 4), 
                new Segment(2, 3), 
                new Segment(5, 2));
        
        ensureThat(segments.rightmost(), eq(5));
    }    
	
	public void shouldReturnAddedSegments() {
		Segments segments1 = new Segments(new Segment[] {
				new Segment(0, 5),
				new Segment(1, 5),
		});
		
		Segments segments2 = new Segments(new Segment[] {
				new Segment(2, 5),
				new Segment(3, 5)
		});
		
		Segments expectedSegments = new Segments(new Segment[] {
				new Segment(0, 5),
				new Segment(1, 5),
				new Segment(2, 5),
				new Segment(3, 5)
		});		
		
		ensureThat(segments1.add(segments2), eq(expectedSegments));
	}	
    
    public void shouldRemoveAGivenSegment() {
        Segments segments = new Segments(
                new Segment(0, 1), 
                new Segment(0, 4), 
                new Segment(0, 3), 
                new Segment(0, 2));
        
        Segments expectedSegments = new Segments(new Segment[] {
                new Segment(0, 1), 
                new Segment(0, 3), 
                new Segment(0, 2)});
        
        Segments actualSegments = segments.remove(new Segment(0, 4));
        
        ensureThat(actualSegments, eq(expectedSegments));
    }
    
    public void shouldReplaceAGivenSegment() {
        Segments segments = new Segments(new Segment[] {
                new Segment(0, 1), 
                new Segment(0, 3), 
                new Segment(0, 2),
                new Segment(1, 5)});
        
        Segments expectedSegments = new Segments(
                new Segment(0, 1), 
                new Segment(0, 3), 
                new Segment(0, 2), 
                new Segment(0, 4));
        
        Segments actualSegments = segments.replace(new Segment(1, 5), new Segment(0, 4));
        
        ensureThat(actualSegments, eq(expectedSegments));
    }
}
