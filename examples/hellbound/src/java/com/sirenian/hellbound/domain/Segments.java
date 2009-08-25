package com.sirenian.hellbound.domain;

import com.sirenian.hellbound.util.Logger;

/**
 * This class should be immutable.
 */
public class Segments {

	public static final Segments EMPTY = new Segments(new Segment[0]);	
	
	private final ImmutableSegments segments;

	public Segments(Segment[] segments) {
		this.segments = new ImmutableSegments(segments);
	}

	public Segments(Segment segment1, Segment segment2, Segment segment3, Segment segment4) {
		this(new Segment[]{segment1, segment2, segment3, segment4});
	}

	public Segment get(int index) {
		return segments.get(index);
	}
	
	public Segments movedRight() {
		return translateWith(new MoveRightTranslator(1));
	}

	public Segments movedRight(int offset) {
		return translateWith(new MoveRightTranslator(offset));
	}
	
	public Segments movedLeft() {
		return translateWith(new MoveRightTranslator(-1));
	}

	public Segments movedDown() {
        return translateWith(new MoveDownTranslator(1));
	}
    

    public Segments movedDown(int offset) {
        return translateWith(new MoveDownTranslator(offset));
    }
	
	public boolean equals(Object obj) {
		Segments that = (Segments) obj;
		
		if (this.size() != that.size()) { return false; }
		
		for (int i = 0; i < segments.size(); i++) {
			if (!this.get(i).equals(that.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < segments.size(); i++) {
			hash = (hash * 32) + segments.get(i).hashCode();
		}
		return hash;
	}

	public int size() {
		return segments.size();
	}

	public boolean contains(Segment segment) {
		for (int i = 0; i < size(); i++) {
			if (segment.equals(get(i))) {
				return true;
			}
		}
		return false;
	}
	
	private Segments translateWith(Translator action) {
		Segment[] newSegments = new Segment[segments.size()];
		for (int i = 0; i < segments.size(); i++) {
			newSegments[i] = action.translate(segments.get(i));
		}
		return new Segments(newSegments);
	}
	
	private static interface Translator {
		Segment translate(Segment segment);
	}
	
	private static class MoveRightTranslator implements Translator {
		private final int offset;
		
		private MoveRightTranslator(int offset) { this.offset = offset; }
		
		public Segment translate(Segment segment) {	return segment.movedRight(offset);	}
	}
    
    private static class MoveDownTranslator implements Translator {
        private final int offset;
        private MoveDownTranslator(int offset) { this.offset = offset; }
        
        public Segment translate(Segment segment) { return segment.movedDown(offset);  }
    }
    
    public String toString() {
        return segments.toString();
    }

	public boolean overlaps(Segments segments) {
		for (int i = 0; i < segments.size(); i++) {
			if (contains(segments.get(i))) {
                Logger.debug(this, "Segments overlap at " + segments.get(i));
				return true;
			}
		}
		return false;
	}

	public int lowest() {
		int lowest = -1;
		for (int i = 0; i < segments.size(); i++) {
			if (segments.get(i).y > lowest) { // heighest int represents lowest posn in the pit
				lowest = segments.get(i).y;
			}
		}
		return lowest;
	}

	public Segments add(Segments segments) {
		Segment[] allSegments = new Segment[this.segments.size() + segments.size()];
		for (int i = 0; i < this.segments.size(); i++)  {
			allSegments[i] = this.segments.get(i);
		}
		for (int i = 0; i < segments.size(); i++) {
			allSegments[this.segments.size() + i] = segments.get(i); 
		}
		return new Segments(allSegments);
	}

    /**
     * @throws IllegalArgumentException if the Segment being removed is not
     * contained in this Segments.
     */
    public Segments remove(Segment segment) {
        Segment[] newSegments = new Segment[segments.size() - 1];
        boolean foundSegment = false;
        for (int i = 0; i < segments.size(); i++) {
            if (segments.get(i).equals(segment)) {
                foundSegment = true;
            } else {
                newSegments[foundSegment ? i - 1 : i] = segments.get(i);
            }
        }
        if (!foundSegment) {
            throw new IllegalArgumentException("Cannot remove " + segment + "; not contained in " + this);
        }
        return new Segments(newSegments);
    }

    /**
     * @throws IllegalArgumentException if the Segment being removed is not
     * contained in this Segments.
     */
    public Segments replace(Segment original, Segment replacement) {
        boolean foundSegment = false;
        Segment[] newSegments = new Segment[segments.size()];
        for (int i = 0; i < segments.size(); i++) {
            if (segments.get(i).equals(original)) {
                foundSegment = true;
                newSegments[i] = replacement;
            } else {
                newSegments[i] = segments.get(i);
            }
        }
        if (!foundSegment) {
            throw new IllegalArgumentException("Cannot replace " + original + " with " + replacement + "; not contained in " + this);
        }
        return new Segments(newSegments);
    }

}
