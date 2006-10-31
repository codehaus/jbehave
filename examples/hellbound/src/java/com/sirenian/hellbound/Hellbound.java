package com.sirenian.hellbound;

import java.awt.Color;
import java.awt.event.WindowListener;

import javax.swing.SwingUtilities;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.game.GameListener;
import com.sirenian.hellbound.domain.game.GameState;
import com.sirenian.hellbound.domain.glyph.Glyph;
import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.engine.Game;
import com.sirenian.hellbound.engine.GlyphFactory;
import com.sirenian.hellbound.engine.PseudoRandomGlyphFactory;
import com.sirenian.hellbound.engine.RequestQueue;
import com.sirenian.hellbound.engine.SelfTimedHeartbeat;
import com.sirenian.hellbound.engine.ThreadedRequestQueue;
import com.sirenian.hellbound.gui.TypeAndColorMap;
import com.sirenian.hellbound.gui.FrontPanel;
import com.sirenian.hellbound.gui.HellboundFrame;
import com.sirenian.hellbound.gui.PitPanel;



public class Hellbound {

	public static final int SCALE = 50;
	public static final int WIDTH = 7;
    public static final int CENTER = 3;
	public static final int HEIGHT = 13;
    public static final TypeAndColorMap COLORMAP = new TypeAndColorMap(GlyphType.ALL, 
            new Color[] {
            Color.RED,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.CYAN,
            Color.BLUE,
            Color.MAGENTA,
            Color.GRAY,
            Color.BLACK
    });
   
	
	public Hellbound() {
		this(new ThreadedRequestQueue(), new SelfTimedHeartbeat(), new PitPanel(SCALE, WIDTH, HEIGHT, COLORMAP), new PseudoRandomGlyphFactory());
	}
	
	public Hellbound(RequestQueue queue, Heartbeat heartbeat, final PitPanel pitPanel, GlyphFactory factory) {
        
        //Set up gui
		final FrontPanel frontPanel = new FrontPanel(queue);
		final HellboundFrame frame = new HellboundFrame(frontPanel, pitPanel);

        // Set up engine
        Game game = new Game(factory, heartbeat, WIDTH, HEIGHT);
        game.addListener(new GlyphListener() {
			public void reportGlyphMovement(final GlyphType type, final Segments origin, final Segments destination) {
				SwingUtilities.invokeLater(new Runnable() { public void run() {pitPanel.reportGlyphMovement(type, origin, destination);}});
			}
			
		});
        
        // Connect gui to engine
        queue.setGameRequestListener(game);
        
        game.addListener(new GameListener() {
			public void reportGameStateChanged(final GameState state) {
				SwingUtilities.invokeLater(new Runnable() { public void run() {frame.reportGameStateChanged(state);}});
			};
        });
        
        
        // Start it up!
        frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Hellbound();
	}
}
