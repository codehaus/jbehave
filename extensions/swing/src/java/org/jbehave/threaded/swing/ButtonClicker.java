package org.jbehave.threaded.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import org.jbehave.core.exception.PendingException;
import org.jbehave.core.threaded.QueuedObjectHolder;
import org.jbehave.core.threaded.TimeoutException;

public class ButtonClicker {
    
    private Idler idler;

    public ButtonClicker() {
        idler = new Idler();
    }
    
    public void click(AbstractButton button) {
        QueueingActionListener queuer = new QueueingActionListener(button);
        
        try {
            button.doClick(200);
            queuer.waitForEvent();
        } finally {
            queuer.removeSelf();
        }
        idler.waitForIdle();
    }

    private class QueueingActionListener implements ActionListener {
        private QueuedObjectHolder holder = new QueuedObjectHolder();
        private final AbstractButton button;
        
        public QueueingActionListener(AbstractButton button) {
            this.button = button;
            button.addActionListener(this);
        }
        
        public void actionPerformed(ActionEvent e) {
            holder.set(e);
        }
        public void waitForEvent() {
            try {
                holder.get(1000);
            } catch (TimeoutException e) {
                throw new PendingException("Clicking buttons is not supported for your Swing library.");
            }
            idler.waitForIdle();
        }
        
        public void removeSelf() {
            button.removeActionListener(this);
        }
        
    }
}
