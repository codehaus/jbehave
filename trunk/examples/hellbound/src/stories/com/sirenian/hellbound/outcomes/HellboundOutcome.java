package com.sirenian.hellbound.outcomes;

import java.awt.Color;

import jbehave.core.mock.Matcher;
import jbehave.core.story.domain.OutcomeUsingMiniMock;
import jbehave.core.story.domain.World;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.gui.RenderedPit;
import com.sirenian.hellbound.stories.Idler;
import com.sirenian.hellbound.stories.WorldKey;
import com.sirenian.hellbound.util.Logger;

public abstract class HellboundOutcome extends OutcomeUsingMiniMock {
	
	protected static final Segments T_SHAPE_AT_TOP = new Segments(
                		new Segment(2, 0),
                		new Segment(3, 0),
                		new Segment(4, 0),
                		new Segment(3, 1)
                );
    
    private Idler idler;

	public HellboundOutcome() {
		idler = new Idler();
	}
	
	public void verify(World world) {
        Logger.debug(this, "verifying Outcome");
		idler.waitForIdle();
		verifyAnyTimeIn(world);
	}
	
	protected abstract void verifyAnyTimeIn(World world);
	
    public Matcher contains(final Segments segments, final Color color) {
    	
        return new Matcher() {
            public boolean matches(Object arg) {
                return ((RenderedPit)arg).contains(segments, color);
            }
            
            public String toString() {                
                StringBuffer buffer = new StringBuffer();
                buffer.append("something containing ").append(System.getProperty("line.separator"));
                for (int y = 0; y < Hellbound.HEIGHT; y++) {
                    for (int x = 0; x < Hellbound.WIDTH; x++) {
                        if (segments.contains(new Segment(x, y))) {
                            buffer.append(Hellbound.COLORMAP.getTypeFor(color).toAscii());
                        } else {
                            buffer.append(GlyphType.PIT.toAscii());
                        }
                    }
                    buffer.append(System.getProperty("line.separator"));
                }
                return buffer.toString();
            }
        };
    }

	protected RenderedPit getPit(World world) {
		return (RenderedPit) world.get(WorldKey.PIT, null);
	}

}
