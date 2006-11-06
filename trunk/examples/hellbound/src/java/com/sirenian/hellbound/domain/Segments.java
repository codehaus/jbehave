package com.sirenian.hellbound.domain;

/**
 * This class should be immutable.
 */
public class Segments {

	private static final Translator MOVE_LEFT = new Translator() {
		public Segment translate(Segment segment) { return segment.movedLeft(); }};
	
	private static final Translator MOVE_RIGHT = new Translator() {
		public Segment translate(Segment segment) { return segment.movedRight(); }};
		
	private static final Translator MOVE_DOWN = new Translator() {
		public Segment translate(Segment segment) { return segment.movedDown(); }};

	private static final Translator COPY = new Translator() {
		public Segment translate(Segment segment) { return segment; }};

	public static final Segments EMPTY = new Segments(new Segment[0]);	
	
	private final Segment[] segments;

	public Segments(Segment[] segments) {
		this.segments = segments;
	}

	public Segments(Segment segment1, Segment segment2, Segment segment3, Segment segment4) {
		this(new Segment[]{segment1, segment2, segment3, segment4});
	}

	public Segment get(int index) {
		return segments[index];
	}


	public Segments copy() {
		return translateWith(COPY);
	}	
	
	public Segments movedRight() {
		return translateWith(MOVE_RIGHT);
	}

	public Segments movedRight(int offset) {
		return translateWith(new MoveRightTranslator(offset));
	}
	
	public Segments movedLeft() {
		return translateWith(MOVE_LEFT);
	}

	public Segments movedDown() {
		return translateWith(MOVE_DOWN);
	}
	
	public boolean equals(Object obj) {
		Segments that = (Segments) obj;
		
		if (this.size() != that.size()) { return false; }
		
		for (int i = 0; i < segments.length; i++) {
			if (!this.get(i).equals(that.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < segments.length; i++) {
			hash = (hash * 32) + segments[i].hashCode();
		}
		return hash;
	}

	public int size() {
		return segments.length;
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
		Segment[] newSegments = new Segment[segments.length];
		for (int i = 0; i < segments.length; i++) {
			newSegments[i] = action.translate(segments[i]);
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
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Segments: ");
        for (int i = 0; i < segments.length; i++) {
            builder.append(segments[i].toString());
        }
        return builder.toString();
    }
}
