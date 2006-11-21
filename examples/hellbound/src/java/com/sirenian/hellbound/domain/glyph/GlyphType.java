/**
 * 
 */
package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;

public class GlyphType {
	private static final int SEGMENT_COUNT = 0;
	
	private final Segments[] segmentsByQuarterTurn;
	private final char ascii;
    
	private GlyphType(char code) { this (null, code); }	
	
	private GlyphType(
			Segments[] positionsByQuarterTurn,
			char ascii) {
				this.segmentsByQuarterTurn = positionsByQuarterTurn;
                this.ascii = ascii;
    }
    
    public String toString() {
        return String.valueOf(ascii);
    }
	
	public static final GlyphType O = new GlyphType(def(
					rotation(0, 0,  1, 0,  1, 1,  0, 1),
					rotation(0, 1,  0, 0,  1, 0,  1, 1),
					rotation(1, 1,  0, 1,  0, 0,  1, 0),
					rotation(1, 0,  1, 1,  0, 1,  0, 0)),
					'O');
	
	public static final GlyphType T = new GlyphType(def(
					rotation(-1, 0,  0, 0,  1, 0,  0, 1),
					rotation(-1, 2,  -1, 1,  -1, 0,  0, 1),
					rotation(0, 1,  0, 1,  -1, 1,  0, 0),
					rotation(0, 0,  0, 1,  0, 2,  -1, 1)),
					'T');
	
	public static final GlyphType I = new GlyphType(def(
					rotation(0, 0,  0, 1,  0, 2,  0, 3),
					rotation(-1, 1,  0, 1,  1, 1,  2, 1),
					rotation(0, 3,  0, 2,  0, 1,  0, 0),
					rotation(2, 1,  1, 1,  0, 1,  -1, 1)),
					'I');
	
	public static final GlyphType J = new GlyphType(def(
					rotation(0, 0,  0, 1,  0, 2,  -1, 2),
					rotation(-1, 0,  -1, 1,  0, 1,  1, 1),
					rotation(-1, 2,  -1, 1,  -1, 0,  0, 0),
					rotation(-1, 0,  0, 0,  1, 0,  1, 1)),
					'J');
	
	public static final GlyphType L = new GlyphType(def(
					rotation(-1, 0,  -1, 1,  -1, 2,  0, 2),
					rotation(1, 0,  0, 0,  -1, 0,  -1, 1),
					rotation(0, 2,  0, 1,  0, 0,  -1, 0),
					rotation(-1, 1,  0, 1,  1, 1,  1, 0)),
					'L');
	
	public static final GlyphType Z = new GlyphType(def(
					rotation(-1, 0,  0, 0,  0, 1,  1, 1),
					rotation(0, 0,  0, 1,  -1, 1,  -1, 2),
					rotation(1, 1,  0, 1,  0, 0,  -1, 0),
					rotation(-1, 2,  0, 1,  0, 1,  0, 0)),
					'Z');
	
	public static final GlyphType S = new GlyphType(def(
					rotation(-1, 1,  0, 1,  0, 0,  1, 0),
					rotation(-1, 0,  -1, 1,  0, 1,  0, 2),
					rotation(1, 0,  0, 0,  0, 1,  -1, 1),
					rotation(0, 2,  0, 1,  -1, 1,  -1, 0)),
					'S');
	
	public static final GlyphType JUNK = new GlyphType('X');
	public static final GlyphType PIT = new GlyphType('.');

    public static final GlyphType ALL_LIVING[] = new GlyphType[] {
        O, T, I, J, L, Z, S
    };
    
    public static final GlyphType[] ALL = new GlyphType[] {
        O, T, I, J, L, Z, S, JUNK, PIT
    };

	
	private static Segments[] def(Segments s1, Segments s2, Segments s3, Segments s4) {
		return new Segments[] { s1, s2, s3, s4 };
	}
	
	private static Segments rotation(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		return new Segments(p(x1, y1), p(x2, y2), p(x3, y3), p(x4, y4));
	}
	
	private static Segment p(int x, int y) {
		return new Segment(x, y);
	}

	public Segments getSegments(int quarterTurnsToLeft) {
		return segmentsByQuarterTurn[quarterTurnsToLeft];
	}

	public int segmentCount() {
		return SEGMENT_COUNT;
	}

	public char toAscii() {
		return ascii;
	}
}