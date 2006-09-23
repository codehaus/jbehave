package com.sirenian.hellbound.domain;

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
		return x + y;
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

	public static Segment[] copy(Segment[] segments) {
		Segment[] copies = new Segment[segments.length];
		for (int i = 0; i < copies.length; i++) {
			copies[i] = segments[i];
		}
		return copies;
	}
	
	public String toString() {
		return "(" + x + "," + y + ")";
	}

    public static Segment[] movedRight(Segment[] segments, int offset) {
        Segment[] copies = new Segment[segments.length];
        for (int i = 0; i < copies.length; i++) {
            copies[i] = segments[i].movedRight(offset);
        }
        return copies;
    }
}
