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
import com.sirenian.hellbound.gui.SwingGuiQueue;
import com.sirenian.hellbound.gui.TypeAndColorMap;

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

    public Hellbound() {
        this(
                new ThreadedEngineQueue(), 
                new SwingGuiQueue(),
                new SelfTimedHeartbeat(), 
                new PitPanel(SCALE, WIDTH, HEIGHT, COLORMAP), 
                new PseudoRandomGlyphFactory());
    }

    public Hellbound(EngineQueue requestQueue, GuiQueue reportQueue,
            Heartbeat heartbeat, PitPanel pitPanel, GlyphFactory factory) {

        HellboundFrame frame = createFrameForGui(requestQueue, pitPanel);
        Game game = createEngineForGame(heartbeat, factory);

        connectQueues(requestQueue, reportQueue, pitPanel, frame, game);
        startHellbound(frame);
    }

    private void startHellbound(HellboundFrame frame) {
        frame.pack();
        frame.setVisible(true);
    }

    private void connectQueues(
            EngineQueue engineQueue,
            GuiQueue guiQueue, 
            GlyphListener pitPanel, 
            HellboundFrame frame,
            Game game) {
        game.addGameListener(guiQueue);
        game.addGlyphListener(guiQueue);
        engineQueue.setGameRequestDelegate(game);
        guiQueue.setGameReportDelegate(frame);
        guiQueue.setGlyphReportDelegate(pitPanel);
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
