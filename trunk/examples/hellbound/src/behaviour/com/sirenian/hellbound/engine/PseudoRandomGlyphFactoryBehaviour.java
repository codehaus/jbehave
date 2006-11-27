package com.sirenian.hellbound.engine;

import jbehave.core.Ensure;
import jbehave.core.minimock.UsingMiniMock;
import jbehave.core.mock.Mock;

import com.sirenian.hellbound.domain.glyph.GlyphListener;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Junk;
import com.sirenian.hellbound.domain.glyph.LivingGlyph;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerSet;

public class PseudoRandomGlyphFactoryBehaviour extends UsingMiniMock {

    public void shouldCreateLivingGlyphsPseudoRandomly() {
        
        PseudoRandomGlyphFactory factory = new PseudoRandomGlyphFactory(42);
        GlyphType[] expected = new GlyphType[] { GlyphType.T,
                GlyphType.Z, GlyphType.S, GlyphType.J, GlyphType.Z,
                GlyphType.L, GlyphType.T, GlyphType.J, GlyphType.S,
                GlyphType.J, GlyphType.J, GlyphType.L, GlyphType.O,
                GlyphType.O, GlyphType.T, GlyphType.J, GlyphType.O,
                GlyphType.Z, GlyphType.O, GlyphType.I, GlyphType.Z
                
        };
        
        for (int i = 0; i < 21; i++) {
            Ensure.that(nextGlyphType(factory) == expected[i]);
        }
    }
    
    public void shouldAddListenersToTheGlyphsThatItCreates() {
        ListenerSet listenerSet = new ListenerSet();
        Mock listener = mock(GlyphListener.class);
        listenerSet.addListener((Listener) listener);
        
        PseudoRandomGlyphFactory factory = new PseudoRandomGlyphFactory(42);
        LivingGlyph glyph = factory.nextGlyph(0, CollisionDetector.NULL, listenerSet);
        Junk junk = factory.createJunk(listenerSet);
        
        listener.expects("reportGlyphMovement").times(2); // once for junk change, once for glyph change
        
        junk.absorb(glyph);
    }

    private GlyphType nextGlyphType(PseudoRandomGlyphFactory factory) {
        return factory.nextGlyph(3, CollisionDetector.NULL, new ListenerSet()).type();
    }
}
