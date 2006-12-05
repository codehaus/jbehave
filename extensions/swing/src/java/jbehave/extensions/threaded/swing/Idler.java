package jbehave.extensions.threaded.swing;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.InvocationEvent;

import javax.swing.SwingUtilities;

public class Idler {
	
	private static final Runnable EMPTY_RUNNABLE = new Runnable() { public void run() {}};
	
	private Toolkit toolkit;
	private EventQueue sysQueue;

	public Idler() {
		toolkit = Toolkit.getDefaultToolkit();
		sysQueue = toolkit.getSystemEventQueue();	
	}
	
	public void waitForIdle() {
		Object lock = new Object();
        synchronized(lock) {
        	sysQueue.postEvent(new InvocationEvent(toolkit, EMPTY_RUNNABLE,
                                             lock, true));
            try { lock.wait(); }
            catch(InterruptedException e) { }
        }
	}
}
