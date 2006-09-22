package com.sirenian.hellbound;

import com.sirenian.hellbound.domain.glyph.LivingGlyphBehaviour;
import com.sirenian.hellbound.gui.FrontPanelBehaviour;
import com.sirenian.hellbound.gui.HellboundFrameBehaviour;
import com.sirenian.hellbound.gui.PitPanelBehaviour;
import com.sirenian.hellbound.util.ListenerSetBehaviour;
import jbehave.core.behaviour.Behaviours;

public class AllBehaviours implements Behaviours {

	public Class[] getBehaviours() {
		return new Class[] {
			HellboundFrameBehaviour.class,
			PitPanelBehaviour.class,
			FrontPanelBehaviour.class,
			LivingGlyphBehaviour.class,
			ListenerSetBehaviour.class
		};
	}

	
}
