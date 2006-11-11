package com.sirenian.hellbound;

import java.awt.Color;

import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.engine.Game;
import com.sirenian.hellbound.engine.GlyphFactory;
import com.sirenian.hellbound.engine.PseudoRandomGlyphFactory;
import com.sirenian.hellbound.engine.GuiQueue;
import com.sirenian.hellbound.engine.EngineQueue;
import com.sirenian.hellbound.engine.SelfTimedHeartbeat;
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

    public Hellbound() {
        this(
                new ThreadedEngineQueue(), 
                new ThreadedSwingQueue(),
                new SelfTimedHeartbeat(), 
                new PitPanel(SCALE, WIDTH, HEIGHT, COLORMAP), 
                new PseudoRandomGlyphFactory());
    }

    public Hellbound(EngineQueue engineQueue, GuiQueue guiQueue,
            Heartbeat heartbeat, PitPanel pitPanel, GlyphFactory factory) {
        
        Logger.debug(this, "Creating Hellbound instance...");

        this.engineQueue = engineQueue;
        this.guiQueue = guiQueue;
        frame = createFrameForGui(engineQueue, pitPanel);
        Game game = createEngineForGame(heartbeat, factory);
        
        connectQueues(engineQueue, guiQueue, pitPanel, frame, game);
        
        startHellbound(frame);
    }

    private void startHellbound(HellboundFrame frame) {
        frame.pack();
        frame.setVisible(true);
    }

    public void stopHellbound() {
        engineQueue.stop();
        guiQueue.stop();
        frame.dispose();
        Logger.debug(this, "Hellbound stopped.");
    }

    private void connectQueues(
            EngineQueue engineQueue,
            GuiQueue guiQueue, 
            GlyphListener pitPanel, 
            HellboundFrame frame,
            Game game) {
        game.addGameListener(guiQueue);
        game.addGlyphListener(guiQueue);
        frame.setGameRequestListener(engineQueue);
        
        engineQueue.setGameRequestDelegate(game);
        guiQueue.setGameListenerDelegate(frame);
        guiQueue.setGlyphListenerDelegate(pitPanel);
    }

    private Game createEngineForGame(Heartbeat heartbeat, GlyphFactory factory) {
        return new Game(factory, heartbeat, WIDTH, HEIGHT);
    }

    private HellboundFrame createFrameForGui(EngineQueue requestQueue,
            PitPanel pitPanel) {
        FrontPanel frontPanel = new FrontPanel(requestQueue);
        return new HellboundFrame(frontPanel, pitPanel);
    }


    public static void main(String[] args) {
        new Hellbound();
    }

}
