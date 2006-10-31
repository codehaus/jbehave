package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;

public class LivingGlyphBehaviour extends GlyphBehaviour {

	
	public void shouldMoveLowerOnHeartbeat() {		
		VerifiableGlyphListener listener = new VerifiableGlyphListener();
		StubHeartbeat heartbeat = new StubHeartbeat();
		
		LivingGlyph glyph = new LivingGlyph(
				heartbeat,
				GlyphType.O,
				4);
		
		glyph.addListener(listener);
		
		Segments firstSegments = listener.recordedSegments;
		
		heartbeat.beat();
		
		Segments secondSegments = listener.recordedSegments;

		ensureThat(secondSegments, eq(firstSegments.movedDown()));
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
	
	public class VerifiableGlyphListener implements GlyphListener {
		private Segments recordedSegments;

		public void reportGlyphMovement(GlyphType type, Segments fromSegments, Segments toSegments) {
			this.recordedSegments = toSegments;
		}
	}
}
