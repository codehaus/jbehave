package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.HeartbeatListener;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;
import com.sirenian.hellbound.util.Logger;

public class AcceleratingHeartbeat implements Heartbeat {

    private static final String RUNNING = "Running";
    private static final String STOPPED = "Stopped";
    private static final String SKIPPING = "Skipping";
    private static final String SKIPPED = "Skipped";
    
	private ListenerSet listenerSet;
	private ListenerNotifier pulse;
	private long timeBetweenBeats;
	private boolean beating;
    private String state;

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
        state = RUNNING;
	}
    
    private synchronized void waitForTheBeat() {
        try {
            wait(timeBetweenBeats);
        } catch (InterruptedException e) { }
        
        if (state == SKIPPING) {
            state = SKIPPED;
        } else {
            Logger.debug(this, "Beating");
            listenerSet.notifyListeners(pulse);
            if (state == SKIPPED) {
                state = RUNNING;
            }
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
        state = STOPPED;
	}

    public boolean isBeating() {
        return beating;
    }

    public void skipNextBeat() {
        state = SKIPPING;
        synchronized(this) {
            this.notifyAll();
        }
    }
}
