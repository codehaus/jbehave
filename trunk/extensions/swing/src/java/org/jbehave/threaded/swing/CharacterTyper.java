package org.jbehave.threaded.swing;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.jbehave.core.exception.PendingException;
import org.jbehave.threaded.time.TimeoutException;

/**
 * Used for pressing or typing all keys which have valid characters associated with them.
 */
class CharacterTyper {

    private static final String TEXT_TYPING_UNSUPPORTED = "Text typing not supported for your Swing library.";
    private static Boolean typingCharWorks;
    private static Boolean pressingCharWorks;
    
    private EventQueue sysQueue;
    private Idler idler;


    CharacterTyper() {
        sysQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        idler = new Idler();
    }
    
    public void typeIntoComponent(Component component, String text) {
        VerifyingKeyAdapter verifier = new VerifyingKeyAdapter();
        component.addKeyListener(verifier);
        
        try {
            for (int i = 0; i < text.length(); i++) {
                postKeyEvent(component, text.charAt(i));
                if (!verifier.keyTyped) {
                    throw new PendingException(TEXT_TYPING_UNSUPPORTED);
                }
            }
        } finally {
            component.removeKeyListener(verifier);
            idler.waitForIdle();
        }
    }
    
    /**
     * Use this for any key which has a valid character associated with it, when it is being pressed
     * (eg: as a game control key) rather than being typed into a text component.
     */
    public void pressKeychar(Window window, char key) throws TimeoutException {
        if (pressingCharWorks == null) {
            postTypedEventWithInputVerifier(window, key);
        } else if (!pressingCharWorks.booleanValue()) {
            throw new PendingException(TEXT_TYPING_UNSUPPORTED);
        } else {
            postKeyEvent(window, key);
        }
    }

    private void postTypedEventWithInputVerifier(Window window, char key) throws TimeoutException {
        boolean result = verifyKeyCharEventPostedToNewFrame(key);
        
        if (result) {
            postTypedEvent(window, key); 
            pressingCharWorks = Boolean.TRUE;
        } else {
            pressingCharWorks = Boolean.FALSE;
            throw new PendingException(TEXT_TYPING_UNSUPPORTED);
        }
    }

    private boolean verifyKeyCharEventPostedToNewFrame(char key) throws TimeoutException {
        JFrame jFrame = new JFrame();
        jFrame.setName("CharacterTyper.frame");
        JPanel panel = new JPanel();
        jFrame.setContentPane(panel);

        
        KeyStroke keyStroke = KeyStroke.getKeyStroke(key);
        String actionId = "CharacterTyper.actionFor " + key;
        VerifyingAction verifier = new VerifyingAction();
        
        panel.getInputMap().put(keyStroke, actionId);
        panel.getActionMap().put(actionId, verifier);
        
        jFrame.pack();
        jFrame.setVisible(true);
        
        new DefaultWindowWrapper("CharacterTyper.frame").getOpenWindow();
        
        postKeyEvent(jFrame, key);
        
        panel.getInputMap().remove(keyStroke);
        panel.getActionMap().remove(actionId);
        
        boolean result = verifier.actionPerformed;
        jFrame.dispose();
        
        idler.waitForIdle();
        
        return result;
    }

    private void postTypedEvent(Window window, char key) throws TimeoutException {
        if (typingCharWorks == null) {
            postTypedEventWithVerifier(window, key);
        } else if (!typingCharWorks.booleanValue()) {
            throw new PendingException(TEXT_TYPING_UNSUPPORTED);
        } else {
            postKeyEvent(window, key);
        }
    }

    private void postTypedEventWithVerifier(Window window, char key) {
        VerifyingKeyAdapter verifier = new VerifyingKeyAdapter();
        window.addKeyListener(verifier);
        
        try {
            postKeyEvent(window, key);
            if (verifier.keyTyped) {
                typingCharWorks = Boolean.TRUE;
            } else {
                typingCharWorks = Boolean.FALSE;
                throw new PendingException(TEXT_TYPING_UNSUPPORTED);
            }
        }
        finally {
            window.removeKeyListener(verifier);
        }
    }

    private void postKeyEvent(Component component, char key) {
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
        
    private class VerifyingKeyAdapter extends KeyAdapter {
        private boolean keyTyped;
        
        public void keyTyped(KeyEvent e) {
            keyTyped = true;
        }
    }
    
    private class VerifyingAction extends AbstractAction {
        private boolean actionPerformed;
        public void actionPerformed(ActionEvent e) {
            actionPerformed = true;
        }
    }

}
