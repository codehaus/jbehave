package com.sirenian.hellbound;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.engine.AcceleratingHeartbeat;
import com.sirenian.hellbound.engine.EngineQueue;
import com.sirenian.hellbound.engine.Game;
import com.sirenian.hellbound.engine.GlyphFactory;
import com.sirenian.hellbound.engine.GuiQueue;
import com.sirenian.hellbound.engine.PseudoRandomGlyphFactory;
import com.sirenian.hellbound.engine.ThreadedEngineQueue;
import com.sirenian.hellbound.gui.FrontPanel;
import com.sirenian.hellbound.gui.HellboundFrame;
import com.sirenian.hellbound.gui.PitPanel;
import com.sirenian.hellbound.gui.ThreadedSwingQueue;
import com.sirenian.hellbound.gui.TypeAndColorMap;
import com.sirenian.hellbound.util.Logger;

public class Hellbound {

    public static final int SCALE = 50;
    public static final int WIDTH = 7;
    public static final int CENTER = 3;
    public static final int HEIGHT = 13;

    public static final TypeAndColorMap COLORMAP = new TypeAndColorMap(
            GlyphType.ALL, 
            new Color[] { 
                    Color.RED, Color.ORANGE, Color.YELLOW,
                    Color.GREEN, Color.CYAN, Color.BLUE, 
                    Color.MAGENTA, Color.GRAY, Color.BLACK });

    private HellboundFrame frame;
    private final EngineQueue engineQueue;
    private final GuiQueue guiQueue;
    private final Heartbeat heartbeat;

    public Hellbound() {
        this(
                new ThreadedEngineQueue(), 
                new ThreadedSwingQueue(),
                new AcceleratingHeartbeat(), 
                new PitPanel(SCALE, WIDTH, HEIGHT, COLORMAP), 
                new PseudoRandomGlyphFactory(42, 7, 13));
    }

    public Hellbound(EngineQueue engineQueue, GuiQueue guiQueue,
            Heartbeat heartbeat, PitPanel pitPanel, GlyphFactory factory) {
        
        this.engineQueue = engineQueue;
        this.guiQueue = guiQueue;
        this.heartbeat = heartbeat;
        Logger.debug(this, "Creating Hellbound instance...");

        frame = createFrameForGui(engineQueue, pitPanel);
        Game game = createEngineForGame(heartbeat, factory);
        
        connectQueues(game, pitPanel);
        bindThreadsToFrame();
        startHellbound();
    }
    
    public static void main(String[] args) {
    	System.getProperties().setProperty("DEBUG", "true");
        new Hellbound();
    }

    private void startHellbound() {
        frame.pack();
        frame.setVisible(true);
    }

    public void stopHellbound() {
        frame.dispose();
        stopThreads(); // Swing cannot be trusted to do this asynchronously
        Logger.debug(this, "Hellbound stopped.");
    }

    private void connectQueues(
            Game game,
            GlyphListener pitPanel) {
        game.addGameListener(guiQueue);
        game.addGlyphListener(guiQueue);
        frame.setGameRequestListener(engineQueue);
        
        engineQueue.setGameRequestDelegate(game);
        guiQueue.setGameListenerDelegate(frame);
        guiQueue.setGlyphListenerDelegate(pitPanel);
        
    }

	private void bindThreadsToFrame() {
        Logger.debug(this, "Binding threads to frame");
		WindowAdapter queueLife = new WindowAdapter() {
            
			public void windowClosing(WindowEvent e) {
                Logger.debug(this, "Window closing; stopping threads");
				stopThreads();
			}
		};
		
		frame.addWindowListener(queueLife);
	}
    
    private void stopThreads() {
        engineQueue.stop();
        guiQueue.stop();
        heartbeat.stop();
    }

    private Game createEngineForGame(Heartbeat heartbeat, GlyphFactory factory) {
        return new Game(factory, heartbeat, WIDTH, HEIGHT);
    }

    private HellboundFrame createFrameForGui(EngineQueue requestQueue,
            PitPanel pitPanel) {
        FrontPanel frontPanel = new FrontPanel(requestQueue);
        return new HellboundFrame(frontPanel, pitPanel);
    }
}
