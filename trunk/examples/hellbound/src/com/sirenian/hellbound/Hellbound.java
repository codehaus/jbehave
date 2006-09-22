package com.sirenian.hellbound;

import java.awt.Color;

import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.engine.Game;
import com.sirenian.hellbound.engine.Glyph;
import com.sirenian.hellbound.engine.GlyphFactory;
import com.sirenian.hellbound.engine.PseudoRandomGlyphFactory;
import com.sirenian.hellbound.engine.RequestQueue;
import com.sirenian.hellbound.engine.SelfTimedHeartbeat;
import com.sirenian.hellbound.engine.ThreadedRequestQueue;
import com.sirenian.hellbound.gui.ColorMap;
import com.sirenian.hellbound.gui.FrontPanel;
import com.sirenian.hellbound.gui.HellboundFrame;
import com.sirenian.hellbound.gui.PitPanel;



public class Hellbound {

	public static final int SCALE = 50;
	public static final int WIDTH = 7;
    public static final int CENTER = 3;
	public static final int HEIGHT = 13;
    public static final ColorMap COLORMAP = new ColorMap(GlyphType.ALL, 
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
	
	public Hellbound(RequestQueue queue, Heartbeat heartbeat, PitPanel pitPanel, GlyphFactory factory) {
        
        //Set up gui
		FrontPanel frontPanel = new FrontPanel(queue);
		HellboundFrame frame = new HellboundFrame(frontPanel, pitPanel);

        // Set up engine
        Game game = new Game();
        Glyph glyph = factory.nextGlyph(heartbeat, CENTER);
        
        // Connect gui to engine
        queue.setGameRequestListener(game);
        game.addListener(frame);
		glyph.addListener(pitPanel);
        
        // Start it up!
        frame.pack();
		frame.setVisible(true);
	}
}
