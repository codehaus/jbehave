package com.sirenian.hellbound.domain.glyph;

public interface Heartbeat {
	
	Heartbeat NULL = new Heartbeat() {
		public void addListener(HeartbeatListener listener) {}
        public boolean isBeating() { return false; }
        public void start(int initialTimeBetweenBeats) {}
        public void stop() {}
	};

	void addListener(HeartbeatListener listener);
    
    void start(int initialTimeBetweenBeats);
    
    void stop();
    
    boolean isBeating();
}
