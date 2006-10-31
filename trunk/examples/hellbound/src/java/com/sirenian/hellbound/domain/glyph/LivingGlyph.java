package com.sirenian.hellbound.domain.glyph;

public class LivingGlyph extends Glyph {
	
	public LivingGlyph(
			Heartbeat heartbeat, 
			GlyphType type,
			int centre) {
		
		super(type, type.nominalSegments(0).movedRight(centre));
		
		heartbeat.addListener(new HeartbeatListener() {
			public void beat() {
				requestMoveDown();
			}
		});
		
		this.type = type;
		
	}
	
	protected void requestMoveDown() {
		moveTo(segments.movedDown());
	}




}
