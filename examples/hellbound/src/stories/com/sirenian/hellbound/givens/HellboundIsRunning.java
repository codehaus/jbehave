package com.sirenian.hellbound.givens;


import java.awt.Graphics;

import jbehave.core.story.domain.GivenUsingMiniMock;
import jbehave.core.story.domain.World;
import jbehave.extensions.threaded.swing.DefaultWindowWrapper;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.engine.EngineQueue;
import com.sirenian.hellbound.engine.GuiQueue;
import com.sirenian.hellbound.engine.PseudoRandomGlyphFactory;
import com.sirenian.hellbound.engine.ThreadedEngineQueue;
import com.sirenian.hellbound.gui.PitPanel;
import com.sirenian.hellbound.gui.RenderedPit;
import com.sirenian.hellbound.gui.SwingGuiQueue;
import com.sirenian.hellbound.stories.WorldKey;

public class HellboundIsRunning extends GivenUsingMiniMock {

	public void setUp(World world) {
		DefaultWindowWrapper hellboundFrameWrapper = new DefaultWindowWrapper("HellboundFrame");		
		ForcedHeartbeat heartbeat = new ForcedHeartbeat();
		RenderedPit graphics = new RenderedPit(Hellbound.SCALE, Hellbound.WIDTH, Hellbound.HEIGHT, Hellbound.COLORMAP);
        EngineQueue requestQueue = new ThreadedEngineQueue();
        GuiQueue guiQueue = new SwingGuiQueue();
		
		new Hellbound(requestQueue, guiQueue, heartbeat, createPitPanelWithDoublePaint(graphics), new PseudoRandomGlyphFactory(42));
		
		world.put(WorldKey.HEARTBEAT, heartbeat);
		world.put(WorldKey.WINDOW_WRAPPER, hellboundFrameWrapper);
		world.put(WorldKey.PIT, graphics);
        world.put(WorldKey.REQUEST_QUEUE, new ThreadedEngineQueue());
	}
	
	private PitPanel createPitPanelWithDoublePaint(final RenderedPit pg) {
		PitPanel panel = new PitPanel(Hellbound.SCALE, Hellbound.WIDTH, Hellbound.HEIGHT, Hellbound.COLORMAP) {

			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				super.paint(g);
				super.paint(pg);
			}
		};
		pg.setPitPanel(panel);
		return panel;
	}	

}
