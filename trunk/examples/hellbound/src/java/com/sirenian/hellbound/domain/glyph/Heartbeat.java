package com.sirenian.hellbound.domain.glyph;

public interface Heartbeat {
	
	Heartbeat NULL = new Heartbeat() {
		public void addListener(HeartbeatListener listener) {}
	};

	void addListener(HeartbeatListener listener);

}
