package org.jbehave.threaded.swing;

import java.awt.EventQueue;
import java.awt.Toolkit;

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
        try {
            SwingUtilities.invokeAndWait(EMPTY_RUNNABLE);
        } catch (Exception ignored) {}
	}
}
