package org.jbehave.threaded.swing;

import java.awt.Component;

import org.jbehave.core.threaded.QueuedObjectHolder;
import org.jbehave.core.threaded.TimeoutException;

public abstract class QueueingAdapter {

    private QueuedObjectHolder holder = new QueuedObjectHolder();
    private Idler idler = new Idler();
    protected final Component component;
    private String eventType;

    public QueueingAdapter(Component component, String eventType) {
        this.component = component;
        this.eventType = eventType;
        addSelfToComponent();
    }

    public void waitForEvent() {
        try {
            holder.get(1000);
        } catch (TimeoutException e) {
            throw new RuntimeException("Expected " + eventType + " on component " + component.getName() + " was not received");
        }
        holder.clear();
        idler.waitForIdle();
    }

    protected abstract void addSelfToComponent();
    
    protected abstract void removeSelfFromComponent();
    
    public void eventOccurred() {
        holder.set(new Object());
    }

}
