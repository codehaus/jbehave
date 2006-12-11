package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.HeartbeatListener;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;
import com.sirenian.hellbound.util.Logger;

public class AcceleratingHeartbeat implements Heartbeat {

	private ListenerSet listenerSet;
	private ListenerNotifier pulse;
	private long timeBetweenBeats;
	private boolean beating;
    private boolean skipping;

	public AcceleratingHeartbeat() {
		listenerSet = new ListenerSet();
		pulse = new ListenerNotifier(){
			public void notify(Listener listener) {
				((HeartbeatListener)listener).beat();
			}};
	}

	public void start(int initialTimeBetweenBeats) {
        stop();
		timeBetweenBeats = initialTimeBetweenBeats;
		beating = true;
		
		Runnable pulseTimer = new Runnable() {
			public void run() {
				while (beating && timeBetweenBeats > 0) {
					waitForTheBeat();
					speedUp();
				}
			}
		};
		
		new Thread(pulseTimer).start();
	}
    
    private synchronized void waitForTheBeat() {
        try {
            wait(timeBetweenBeats);
        } catch (InterruptedException e) { }
        
        if (!skipping) {
            Logger.debug(this, "Beating");
            listenerSet.notifyListeners(pulse);
        }
    }       

    private void speedUp() {
        timeBetweenBeats--;
    }       
    
	public void addListener(HeartbeatListener listener) {
		listenerSet.addListener(listener);
	}

	public void stop() {
	    beating = false;
        synchronized (this) {
            notifyAll();
        }
	}

    public boolean isBeating() {
        return beating;
    }

    public void skipNextBeat() {
        skipping = true;
        synchronized(this) {
            this.notifyAll();
        }
        skipping = false;
    }
}
