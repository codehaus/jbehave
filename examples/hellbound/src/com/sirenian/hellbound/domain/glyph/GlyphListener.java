/**
 * 
 */
package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.util.Listener;

public interface GlyphListener extends Listener {
	
	void reportGlyphMovement(GlyphType type, Segment[] origin, Segment[] destination);
}