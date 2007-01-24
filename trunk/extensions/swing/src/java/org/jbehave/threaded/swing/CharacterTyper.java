package org.jbehave.threaded.swing;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.jbehave.core.exception.PendingException;
import org.jbehave.core.threaded.QueuedMiniHashMap;
import org.jbehave.core.threaded.TimeoutException;

/**
 * Used for pressing or typing all keys which have valid characters associated with them.
 */
class CharacterTyper {

    private static final String ACTION_KEY = "CharacterTyper.action";
    private static final String TEXT_TYPING_UNSUPPORTED = "Text typing not supported for your Swing library.";
    
    private EventQueue sysQueue;
    private Idler idler;


    CharacterTyper() {
        sysQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        idler = new Idler();
    }
    
    public void typeIntoComponent(Component component, String text) {
        QueueingKeyAdapter queuer = new QueueingKeyAdapter(component);
        try {
            for (int i = 0; i < text.length(); i++) {
                postKeyEvent(component, text.charAt(i));
                queuer.waitForEvent();
            }
        } finally {
            queuer.removeSelf();
        }
    }
    
    /**
     * Use this for any key which has a valid character associated with it, when it is being pressed
     * (eg: as a game control key) rather than being typed into a text component.
     */
    public void pressKeychar(Window window, char key) throws TimeoutException {
        QueueingKeyAdapter queuer = null;
        
        if(window instanceof JFrame) {
            Container contentPane = ((JFrame)window).getContentPane();
            if (contentPane instanceof JComponent) {
                queuer = new QueueingKeyAdapter(contentPane);
            }
        } else {
            queuer = new QueueingKeyAdapter(window.getFocusOwner());
        }
        
        postKeyEvent(window, key);
        
        try {
            queuer.waitForEvent();
        } finally {
            queuer.removeSelf();
        }
    }

    private void postKeyEvent(final Component component, final char key) {
        sysQueue.postEvent(createKeyPressEvent(component, key, KeyEvent.KEY_PRESSED));
        sysQueue.postEvent(createKeyPressEvent(component, key, KeyEvent.KEY_RELEASED));
        sysQueue.postEvent(createKeyPressEvent(component, key, KeyEvent.KEY_TYPED));    
        idler.waitForIdle();
    }
    
    private AWTEvent createKeyPressEvent(Component component, char c, int id) {
        return new KeyEvent(component, 
                id, 
                System.currentTimeMillis(),
                0,
                KeyEvent.VK_UNDEFINED,
                c);
    }
        
    private class QueueingKeyAdapter extends KeyAdapter {
        private QueuedMiniHashMap map = new QueuedMiniHashMap();
        private final Component component;
        
        public QueueingKeyAdapter(Component component) {
            this.component = component;
            component.requestFocus();
            component.addKeyListener(this);
        }
        public void keyTyped(KeyEvent e) {
            map.put("keyTyped", e);
        }
        public void waitForEvent() {
            try {
                map.get("keyTyped", 1000);
            } catch (TimeoutException e) {
                throw new PendingException(TEXT_TYPING_UNSUPPORTED);
            }
            idler.waitForIdle();
        }
        
        public void removeSelf() {
            component.removeKeyListener(this);
        }
    }       
}
