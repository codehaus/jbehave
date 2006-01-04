package jbehave.extensions.harness;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.AWTEventListener;
import java.awt.event.WindowEvent;

import jbehave.extensions.harness.time.TimeoutException;


/**
 * Stores windows as they are opened for later retrieval. Windows will be stored by title,
 * then by name if the title is null, then by class if the name is null. Only one window for
 * each key calculated in this way will be stored.
 */
public class WindowGrabber {
    public static final long DEFAULT_WINDOW_TIMEOUT = 30000;
    
    private QueuedMiniMap miniMap;
    private AWTEventListener windowWatcher = new WindowWatcher();
    
    public WindowGrabber() {
    	this(new QueuedMiniHashMap());
    }
    
    public WindowGrabber(QueuedMiniMap miniMap) {
        this.miniMap = miniMap;
        
        Toolkit.getDefaultToolkit().addAWTEventListener(
                windowWatcher, 
                AWTEvent.WINDOW_EVENT_MASK);
    }

    public Window getWindow(String title) throws TimeoutException {
        return (Window)miniMap.get(title, DEFAULT_WINDOW_TIMEOUT);
    }
    
    public void dispose() {
        Toolkit.getDefaultToolkit().removeAWTEventListener(windowWatcher);
    }
    
    private class WindowWatcher implements AWTEventListener {
        public void eventDispatched(AWTEvent event) {
            if (event.getID() == WindowEvent.WINDOW_OPENED) {
                handleWindowOpened(event);  
            } else if (event.getID() == WindowEvent.WINDOW_CLOSED) {
                handleWindowClosed(event);
            }
        }

        private void handleWindowClosed(AWTEvent event) {        	
            Window window = (Window)event.getSource();
            miniMap.remove(window.getName());
        }

        private void handleWindowOpened(AWTEvent event) {
            Window window = (Window)event.getSource();
            miniMap.put(window.getName(), window);
        }
    };
}
