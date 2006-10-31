package com.sirenian.hellbound.domain.glyph;

public class StubHeartbeat implements Heartbeat {
	
	private HeartbeatListener listener;

	public void addListener(HeartbeatListener listener) {
		this.listener = listener;
	}
	
	public void beat() {
		listener.beat();
	}
}
