package com.sirenian.hellbound;

import jbehave.core.behaviour.Behaviours;

import com.sirenian.hellbound.domain.SegmentsBehaviour;
import com.sirenian.hellbound.domain.glyph.GlyphBehaviour;
import com.sirenian.hellbound.domain.glyph.JunkBehaviour;
import com.sirenian.hellbound.domain.glyph.LivingGlyphBehaviour;
import com.sirenian.hellbound.engine.GameBehaviour;
import com.sirenian.hellbound.engine.PseudoRandomGlyphFactoryBehaviour;
import com.sirenian.hellbound.engine.AcceleratingHeartbeatBehaviour;
import com.sirenian.hellbound.engine.ThreadedEngineQueueBehaviour;
import com.sirenian.hellbound.gui.FrontPanelBehaviour;
import com.sirenian.hellbound.gui.HellboundFrameBehaviour;
import com.sirenian.hellbound.gui.PitPanelBehaviour;
import com.sirenian.hellbound.util.ListenerSetBehaviour;
import com.sirenian.hellbound.util.ThreadedQueueBehaviour;

public class AllBehaviours implements Behaviours {

	public Class[] getBehaviours() {
		return new Class[] {
			SegmentsBehaviour.class,
			
			LivingGlyphBehaviour.class,
			GlyphBehaviour.class,
			PseudoRandomGlyphFactoryBehaviour.class,
			AcceleratingHeartbeatBehaviour.class,
            GameBehaviour.class,
			
			ThreadedQueueBehaviour.class,
			ThreadedEngineQueueBehaviour.class,
			ThreadedQueueBehaviour.class,
			ListenerSetBehaviour.class,				
				
			FrontPanelBehaviour.class,
			HellboundFrameBehaviour.class,
			PitPanelBehaviour.class,
            JunkBehaviour.class
		};
	}

	
}
