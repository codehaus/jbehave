package com.sirenian.hellbound.engine;

import jbehave.core.Ensure;
import jbehave.core.minimock.UsingMiniMock;

import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;

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

    private GlyphType nextGlyphType(PseudoRandomGlyphFactory factory) {
        return factory.nextGlyph((Heartbeat) stub(Heartbeat.class), 3).type;
    }
}
