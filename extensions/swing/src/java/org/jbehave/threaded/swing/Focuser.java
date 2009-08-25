package org.jbehave.threaded.swing;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Focuser {

    public void requestFocusOn(Component component) {
        if (!component.hasFocus()) {
            QueueingFocusListener focusListener = new QueueingFocusListener(component);
            component.requestFocus();
            try {
                focusListener.waitForEvent();
            } finally {
                focusListener.removeSelfFromComponent();
            }
        }
    }

    private class QueueingFocusListener extends QueueingAdapter implements FocusListener {
        
        public QueueingFocusListener(Component component) {
            super(component, "FocusEvent");
        }
        
        public void focusGained(FocusEvent e) {
            eventOccurred();
        }

        public void focusLost(FocusEvent e) {}
        
        protected void addSelfToComponent() {
            component.addFocusListener(this);
        }

        protected void removeSelfFromComponent() {
            component.removeFocusListener(this);
        }
    }   
}
