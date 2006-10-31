package com.sirenian.hellbound.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;

public class PitPanel extends JPanel implements GlyphListener {

	private static final long serialVersionUID = -5533752426214026189L;
    
	private Map TYPEMAP = new HashMap();
	
	private int scale;
	private int pitWidth;
	private int pitHeight;

    private TypeAndColorMap colorMap;

	public PitPanel(int scale, int width, int height, TypeAndColorMap colorMap) {
		this.scale = scale;
		this.pitWidth = width;
		this.pitHeight = height;
        this.colorMap = colorMap;
        
		this.setPreferredSize(new Dimension(scale * pitWidth, scale * pitHeight));
	}

	public void reportGlyphMovement(GlyphType type, Segments fromSquares, Segments toSquares) {
		for (int i = 0; i < fromSquares.size(); i++) {
			TYPEMAP.remove(fromSquares.get(i));
		}
		for (int i = 0; i < toSquares.size(); i++) {
			TYPEMAP.put(toSquares.get(i), type);
		}
        repaint();
	}
	
	public void paint(Graphics g) {
		for (int i = 0; i < pitWidth; i++) {
			for (int j = 0; j < pitHeight; j++) {
				GlyphType type = (GlyphType)TYPEMAP.get(new Segment(i, j));
				if (type == null) {type = GlyphType.PIT; }
				Color color = colorMap.getColorFor(type);
				g.setColor(color);
				g.fillRect(getBounds().x + (scale * i), getBounds().y + (scale * j), scale, scale);
			}
		}
	}
}
