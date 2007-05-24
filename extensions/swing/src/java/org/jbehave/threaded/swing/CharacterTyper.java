package org.jbehave.threaded.swing;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.ItemSelectable;
import java.awt.TextComponent;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.jbehave.core.threaded.TimeoutException;

/**
 * Used for pressing or typing all keys which have valid characters associated with them.
 */
class CharacterTyper {

    private EventQueue sysQueue;
    private Idler idler;
    private Focuser focuser;


    CharacterTyper() {
        sysQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        idler = new Idler();
        focuser = new Focuser();
    }
    
    public void typeIntoComponent(Component component, String text) {
        focuser.requestFocusOn(component);
        QueueingAdapter queuer;
        
        if (component instanceof JComboBox && ((JComboBox)component).isEditable()) {
            queuer = new QueueingKeyAdapter(((JComboBox)component).getEditor().getEditorComponent());
        } else {
            queuer = new QueueingKeyAdapter(component);
        }
        
        try {
            for (int i = 0; i < text.length(); i++) {
                postKeyEvent(component, text.charAt(i));
                queuer.waitForEvent();
            }
        } finally {
            queuer.removeSelfFromComponent();
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
                focuser.requestFocusOn(contentPane);
            }
        } else {
            queuer = new QueueingKeyAdapter(window.getFocusOwner());
            focuser.requestFocusOn(window.getFocusOwner());
        }
        
        postKeyEvent(window, key);
        
        try {
            queuer.waitForEvent();
        } finally {
            queuer.removeSelfFromComponent();
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
        
    private class QueueingKeyAdapter extends QueueingAdapter implements KeyListener {
        
        public QueueingKeyAdapter(Component component) {
            super(component, "KeyEvent");
        }
        
        public void keyTyped(KeyEvent e) {
            eventOccurred();
        }
        
        protected void addSelfToComponent() {
            component.addKeyListener(this);
        }
        protected void removeSelfFromComponent() {
            component.removeKeyListener(this);
        }
        
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }
    }
}
