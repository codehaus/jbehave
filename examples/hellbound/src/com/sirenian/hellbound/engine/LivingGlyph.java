package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.Segment;
import com.sirenian.hellbound.domain.glyph.GlyphType;
import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.HeartbeatListener;



public class LivingGlyph extends Glyph {
	
	public LivingGlyph(
			Heartbeat heartbeat, 
			GlyphType type,
			int centre) {
		
		super(type, Segment.movedRight(Segment.copy(type.rotationsAtRoot[0]), centre)); // will need to move to pit centre
		
		heartbeat.addListener(new HeartbeatListener() {
			public void beat() {
				requestMoveDown();
			}
		});
		
		this.type = type;
		
	}
	
	protected void requestMoveDown() {
		Segment[] newSegments = new Segment[type.segmentCount];
		for (int i = 0; i < type.segmentCount; i++) {
			newSegments[i] = segments[i].movedDown();
		}
		moveTo(newSegments);
	}

}
