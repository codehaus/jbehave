package com.sirenian.hellbound.domain;

/**
 * This class should be immutable.
 */
public class Segment {
	public final int x;
	public final int y;
	
	public Segment(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o) {
		if (o.getClass() != getClass()) return false;
		Segment s = (Segment)o;
		return (s.x == x) && (s.y == y);
	}
	
	public int hashCode() {
		return x * 32 + y;
	}

	public Segment movedDown() {
		return new Segment(x, y + 1);
	}
	
	public Segment movedLeft() {
		return new Segment(x - 1, y);
	}
	
	public Segment movedRight() {
        return movedRight(1);
	}

    public Segment movedRight(int offset) {
        return new Segment(x + offset, y);
    }
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
