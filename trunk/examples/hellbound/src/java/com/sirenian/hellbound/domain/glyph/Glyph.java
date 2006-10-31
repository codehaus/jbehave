package com.sirenian.hellbound.domain.glyph;

import com.sirenian.hellbound.domain.Segments;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;

public class Glyph {
	
	protected GlyphType type;
	protected Segments previousSegments;
	protected Segments segments;
	
	private ListenerSet listeners;
	private ListenerNotifier glyphMovementNotifier;

	public Glyph(GlyphType type, Segments firstPosition) {
		this.listeners = new ListenerSet();
		this.type = type;
		
		previousSegments = firstPosition;
		segments = firstPosition;
		
		glyphMovementNotifier = new ListenerNotifier() {
			public void notify(Listener listener) {
				((GlyphListener)listener).reportGlyphMovement(Glyph.this.type, previousSegments, segments);
			}
		};
	}

	public void addListener(GlyphListener listener) {
		listeners.addListener(listener);
		
		listener.reportGlyphMovement(type, previousSegments, segments);
	}


	public void addListeners(ListenerSet listeners) {
		this.listeners.addListeners(listeners);
		listeners.notifyListeners(glyphMovementNotifier);
	}
	
	protected void moveTo(Segments destination) {
		this.previousSegments = segments;
		this.segments = destination;
		
		listeners.notifyListeners(glyphMovementNotifier);
	}

	public GlyphType type() {
		return type;
	}

}
