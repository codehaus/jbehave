package com.sirenian.hellbound.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.sirenian.hellbound.domain.glyph.GlyphType;

public class ColorMap {
    
    private Map typesToColors = new HashMap();
    private Map colorsToTypes = new HashMap();

    public ColorMap(GlyphType[] types, Color[] colors) {
        for (int i = 0; i < types.length; i++) {
            typesToColors.put(types[i], colors[i]);
            colorsToTypes.put(colors[i], types[i]);
        }
    }
    
    public Color getColorFor(GlyphType t) {
        Color color = (Color) typesToColors.get(t);
        return color == null ? Color.BLACK : color;
    }
    
    public GlyphType getTypeFor(Color c) {
        return (GlyphType) colorsToTypes.get(c);
    }

    public char getAsciiFor(Color c) {
        return getTypeFor(c).ascii;
    }

}
