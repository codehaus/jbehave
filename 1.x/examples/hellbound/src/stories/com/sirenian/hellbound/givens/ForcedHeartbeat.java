package com.sirenian.hellbound.givens;

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
    private boolean beating;
    private boolean skipped;
	
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
		if (System.getProperty("com.sirenian.hellbound.SLOW") != null) {
			synchronized(this) {
				try {
					wait(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		listeners.notifyListeners(pulse);
        skipped = false;
	}

    public void start(int initialTimeBetweenBeats) {
        beating = true;
        skipped = false;
    }

    public void stop() {
        beating = false;
        skipped = false;
    }

    public boolean isBeating() {
        return beating;
    }

    public boolean wasSkipped() {
        return skipped;
    }

    public void skipNextBeat() {
        skipped = true;
    }

}
