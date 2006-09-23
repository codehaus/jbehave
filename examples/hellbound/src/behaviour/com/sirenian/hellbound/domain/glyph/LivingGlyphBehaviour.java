package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segment;
import jbehave.core.Ensure;

public class LivingGlyphBehaviour extends GlyphBehaviour {

	
	public void shouldMoveLowerOnHeartbeat() {		
		VerifiableGlyphListener listener = new VerifiableGlyphListener();
		ArtificialHeartbeat heartbeat = new ArtificialHeartbeat();
		
		LivingGlyph glyph = new LivingGlyph(
				heartbeat,
				GlyphType.O,
				4);
		
		glyph.addListener(listener);
		
		Segment[] firstSegments = Segment.copy(listener.recordedSegments);
		
		heartbeat.listener.beat();
		
		Segment[] secondSegments = Segment.copy(listener.recordedSegments);

		for (int i = 0; i < GlyphType.O.segmentCount; i++) {
			Ensure.that(secondSegments[i], eq(firstSegments[i].movedDown()));
		}		
	}
	
	public void shouldNotMoveIfInCollisionWithWalls() {
		// TODO
	}
	
	public void shouldNotMoveIfInCollisionWithDetritus() {
		// TODO
	}
	
	public void shouldQueueAttemptsToMoveIfAlreadyMoving() {
		// TODO
	}
	
	public void shouldClearAllQueuedDownwardMovementsOnHeartbeat() {
		// TODO
	}
	
	private class ArtificialHeartbeat implements Heartbeat {
		private HeartbeatListener listener;

		public void addListener(HeartbeatListener listener) {
			this.listener = listener;
		}		
	}
	
	public class VerifiableGlyphListener implements GlyphListener {
		private Segment[] recordedSegments;

		public void reportGlyphMovement(GlyphType type, Segment[] fromSegments, Segment[] toSegments) {
			this.recordedSegments = toSegments;
		}
	}
}
