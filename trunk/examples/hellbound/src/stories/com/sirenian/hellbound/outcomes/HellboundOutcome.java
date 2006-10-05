package com.sirenian.hellbound.outcomes;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import jbehave.core.mock.Constraint;
import jbehave.core.story.domain.OutcomeUsingMiniMock;

import com.sirenian.hellbound.Hellbound;
import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.gui.RenderedPit;

public abstract class HellboundOutcome extends OutcomeUsingMiniMock {

    public Constraint contains(final Segment[] segments, final Color color) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return ((RenderedPit)arg).contains(segments, color);
            }
            
            public String toString() {                
                StringBuffer buffer = new StringBuffer();
                buffer.append("something containing ").append(System.getProperty("line.separator"));
                List segmentList = Arrays.asList(segments);
                for (int y = 0; y < Hellbound.HEIGHT; y++) {
                    for (int x = 0; x < Hellbound.WIDTH; x++) {
                        if (segmentList.contains(new Segment(x, y))) {
                            buffer.append(Hellbound.COLORMAP.getAsciiFor(color));
                        } else {
                            buffer.append(GlyphType.PIT.ascii);
                        }
                    }
                    buffer.append(System.getProperty("line.separator"));
                }
                return buffer.toString();
            }
        };
    }

}
