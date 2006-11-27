package com.sirenian.hellbound.domain.glyph;

public class StubHeartbeat implements Heartbeat {
	
	private HeartbeatListener listener;
    private boolean beating;

	public void addListener(HeartbeatListener listener) {
		this.listener = listener;
	}
	
	public void beat() {
		listener.beat();
	}

    public void start(int initialTimeBetweenBeats) {
        beating = true;
    }

    public void stop() {
        
    }

    public boolean isBeating() {
        return beating;
    }
}
