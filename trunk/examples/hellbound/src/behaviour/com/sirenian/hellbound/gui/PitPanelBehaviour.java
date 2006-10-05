package com.sirenian.hellbound.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import jbehave.core.mock.Constraint;
import jbehave.core.mock.UsingConstraints;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.glyph.GlyphType;

public class PitPanelBehaviour extends UsingConstraints {

	// Can't yet find a way of checking the colours in the pit!
	
	private static final int HEIGHT = 5;
	private static final int WIDTH = 4;
	private static final int SCALE = 50;
    private static final ColorMap COLORMAP = new ColorMap(new GlyphType[]{GlyphType.O, GlyphType.PIT}, new Color[]{Color.RED, Color.BLACK});

	Segment[] INITIAL_SEGMENTS = new Segment[] {
			new Segment(1, 2),
			new Segment(1, 3),
			new Segment(2, 3),
			new Segment(3, 3)
	};	
	
	Segment[] FINAL_SEGMENTS = new Segment[] {
			new Segment(1, 2),
			new Segment(1, 3),
			new Segment(2, 3),
			new Segment(3, 3)
	};
	
	public void shouldBeScaledToFitThePit() {
		JFrame frame = new JFrame();
		PitPanel panel = new PitPanel(SCALE, WIDTH, HEIGHT, Hellbound.COLORMAP);		
		frame.getContentPane().add(panel);
		frame.setVisible(true);		
		ensureThat(panel.getPreferredSize(), eq(new Dimension(200, 250)));		
		frame.dispose();
	}
	
	public void shouldDrawGlyphsAndRepaintWithNoError() {
		
		final RenderedPit renderedPit = new RenderedPit(SCALE, WIDTH, HEIGHT, COLORMAP);
		
		PitPanel panel = createPitPanelWithDoublePaint(renderedPit);

		JFrame frame = createFrameAndDisplay(panel);
		
		panel.reportGlyphMovement(GlyphType.O, new Segment[0], INITIAL_SEGMENTS);
		
		panel.repaint();
		frame.dispose();		
        
        ensureThat(renderedPit, contains(INITIAL_SEGMENTS, Color.RED));
	}



	public void shouldCleanGlyphsAfterMovement() {
		
		RenderedPit renderedPit = new RenderedPit(SCALE, WIDTH, HEIGHT, COLORMAP);
		PitPanel panel = createPitPanelWithDoublePaint(renderedPit);

		JFrame frame = createFrameAndDisplay(panel);
			
		
		panel.reportGlyphMovement(GlyphType.O, new Segment[0], INITIAL_SEGMENTS);
		
		panel.reportGlyphMovement(GlyphType.O, INITIAL_SEGMENTS, FINAL_SEGMENTS);
		
		panel.repaint();
		frame.dispose();		
		
		ensureThat(renderedPit, contains(FINAL_SEGMENTS, Color.RED));
	}

	private JFrame createFrameAndDisplay(PitPanel panel) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		return frame;
	}

	private PitPanel createPitPanelWithDoublePaint(final RenderedPit pg) {
		PitPanel panel = new PitPanel(SCALE, WIDTH, HEIGHT, Hellbound.COLORMAP) {

			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				super.paint(g);
				super.paint(pg);
			}
		};
		pg.setPitPanel(panel);
		return panel;
	}
    
    public Constraint contains(final Segment[] segments, final Color color) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return ((RenderedPit)arg).contains(segments, color);
            }
            
            public String toString() {                
                StringBuffer buffer = new StringBuffer();
                buffer.append("something containing ").append(System.getProperty("line.separator"));
                List segmentList = Arrays.asList(segments);
                for (int y = 0; y < Hellbound.HEIGHT; y++) {
                    for (int x = 0; x < Hellbound.WIDTH; x++) {
                        if (segmentList.contains(new Segment(x, y))) {
                            buffer.append(Hellbound.COLORMAP.getAsciiFor(color));
                        } else {
                            buffer.append(GlyphType.PIT.ascii);
                        }
                    }
                    buffer.append(System.getProperty("line.separator"));
                }
                return buffer.toString();
            }
        };
    }
}
