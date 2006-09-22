/**
 * 
 */
package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segment;

public class GlyphType {

	/**
	 * It's easier to put these here than it is to work out some
	 * complex way of preventing anyone from changing the contents
	 * of the array. Don't change the definition of the type! OK?
	 * There's a nice little 'copy' function you can use in Square 
	 * instead.
	 */
	public final Segment[][] rotationsAtRoot;
	public final Segment root;
	public final int segmentCount = 4; // not constant as may not be 4 for all glyphs in future
    public final char ascii;
    
	private GlyphType(char code) { this (null, null, code); }	
	
	private GlyphType(
			Segment[][] rotationsAtRoot,
			Segment root,
            char ascii) {
				this.rotationsAtRoot = rotationsAtRoot;
				this.root = root;
                this.ascii = ascii; }
    
    public String toString() {
        return super.toString() + ":" + ascii;
    }
	
	public static final GlyphType O = new GlyphType(def(
					rotation(0, 0,  1, 0,  1, 1,  0, 1),
					rotation(0, 1,  0, 0,  1, 0,  1, 1),
					rotation(1, 1,  0, 1,  0, 0,  1, 0),
					rotation(1, 0,  1, 1,  0, 1,  0, 0)),
					p(0, 0), 'O');
	
	public static final GlyphType T = new GlyphType(def(
					rotation(0, 0,  1, 0,  2, 0,  1, 1),
					rotation(0, 2,  0, 1,  0, 0,  1, 1),
					rotation(2, 1,  1, 1,  0, 1,  1, 0),
					rotation(1, 0,  1, 1,  1, 2,  0, 1)),
					p(1, 0), 'T');
	
	public static final GlyphType I = new GlyphType(def(
					rotation(1, 0,  1, 1,  1, 2,  1, 3),
					rotation(0, 1,  1, 1,  2, 1,  3, 1),
					rotation(1, 3,  1, 2,  1, 1,  1, 0),
					rotation(3, 1,  2, 1,  1, 1,  0, 1)),
					p(1, 0), 'I');
	
	public static final GlyphType J = new GlyphType(def(
					rotation(1, 0,  1, 1,  1, 2,  0, 2),
					rotation(2, 1,  1, 1,  0, 1,  0, 0),
					rotation(0, 2,  0, 1,  0, 0,  1, 0),
					rotation(0, 0,  1, 0,  2, 0,  2, 1)),
					p(1, 0), 'J');
	
	public static final GlyphType L = new GlyphType(def(
					rotation(0, 0,  0, 1,  0, 2,  1, 2),
					rotation(2, 0,  1, 0,  0, 0,  0, 1),
					rotation(1, 2,  1, 1,  1, 0,  0, 0),
					rotation(0, 1,  1, 1,  2, 1,  2, 0)),
					p(1, 0), 'L');
	
	public static final GlyphType Z = new GlyphType(def(
					rotation(0, 0,  1, 0,  1, 1,  2, 1),
					rotation(1, 0,  1, 1,  0, 1,  0, 2),
					rotation(2, 1,  1, 1,  1, 0,  0, 0),
					rotation(0, 2,  0, 1,  1, 1,  1, 0)),
					p(1, 0), 'Z');
	
	public static final GlyphType S = new GlyphType(def(
					rotation(0, 1,  1, 1,  1, 0,  2, 0),
					rotation(0, 0,  0, 1,  1, 1,  1, 2),
					rotation(2, 0,  1, 0,  1, 1,  0, 1),
					rotation(1, 2,  1, 1,  0, 1,  0, 0)),
					p(1, 0), 'S');
	
	public static final GlyphType JUNK = new GlyphType('X');
	public static final GlyphType PIT = new GlyphType('.');

    public static final GlyphType ALL_LIVING[] = new GlyphType[] {
        O, T, I, J, L, Z, S
    };
    
    public static final GlyphType[] ALL = new GlyphType[] {
        O, T, I, J, L, Z, S, JUNK, PIT
    };
	
	private static Segment[][] def(Segment[] s1, Segment[] s2, Segment[] s3, Segment[] s4) {
		return new Segment[][] { s1, s2, s3, s4 };
	}
	
	private static Segment[] rotation(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		return new Segment[] { p(x1, y1), p(x2, y2), p(x3, y3), p(x4, y4) };
	}
	
	private static Segment p(int x, int y) {
		return new Segment(x, y);
	}
}