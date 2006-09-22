package com.sirenian.hellbound.engine;

import com.sirenian.hellbound.domain.glyph.Heartbeat;
import com.sirenian.hellbound.domain.glyph.HeartbeatListener;
import com.sirenian.hellbound.util.Listener;
import com.sirenian.hellbound.util.ListenerNotifier;
import com.sirenian.hellbound.util.ListenerSet;

public class SelfTimedHeartbeat implements Heartbeat {

	private ListenerSet listenerSet;
	private ListenerNotifier pulse;
	private long timeBetweenBeats;
	private boolean beating;

	public SelfTimedHeartbeat() {
		listenerSet = new ListenerSet();
		pulse = new ListenerNotifier(){
			public void notify(Listener listener) {
				((HeartbeatListener)listener).beat();
			}};
	}

	public void start(long initialTimeBetweenBeats) {
		timeBetweenBeats = initialTimeBetweenBeats;
		beating = true;
		
		Runnable pulseTimer = new Runnable() {
			public void run() {
				while (beating) {
					synchronized(this) {
						try {
							wait(timeBetweenBeats);
						} catch (InterruptedException e) { }
						listenerSet.notifyListeners(pulse);
					}
				}
			}			
		};
		
		new Thread(pulseTimer).start();
	}

	public void addListener(HeartbeatListener listener) {
		listenerSet.addListener(listener);
	}

	public void stop() {
		beating = false;
	}

}
