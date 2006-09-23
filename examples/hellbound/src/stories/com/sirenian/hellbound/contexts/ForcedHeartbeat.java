package com.sirenian.hellbound.contexts;

import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.HeartbeatListener;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;

/**
 * Used in place of the real heartbeat in order to run stories more quickly.
 */
public class ForcedHeartbeat implements Heartbeat {

	private ListenerSet listeners;
	private ListenerNotifier pulse;
	
	public ForcedHeartbeat() {
		listeners = new ListenerSet();
		pulse = new ListenerNotifier() {
			public void notify(Listener listener) {
				((HeartbeatListener)listener).beat();
			}			
		};
	}
	
	public void addListener(HeartbeatListener listener) {
		listeners.addListener(listener);
	}
	
	public void causeBeat() {
		listeners.notifyListeners(pulse);
	}

}
