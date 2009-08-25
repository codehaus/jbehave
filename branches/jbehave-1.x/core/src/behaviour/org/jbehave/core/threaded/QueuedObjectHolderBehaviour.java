package org.jbehave.core.threaded;

import org.jbehave.core.mock.UsingMatchers;

public class QueuedObjectHolderBehaviour extends UsingMatchers {
    
    public void shouldReturnExistingObject() throws TimeoutException {
        QueuedObjectHolder holder = new QueuedObjectHolder(1000);
        holder.set("Frodo");
        ensureThat(holder.get(), eq("Frodo"));
    }
    
    public void shouldReturnObjectWhenAddedLater() {
        final QueuedObjectHolder holderUnderTest = new QueuedObjectHolder(1000);
        
        final ObjectHolder ourHolder = new ObjectHolder();
        
        Thread threadForGetToRun = new Thread(new Runnable() {
            public void run() {
                try {
                    ourHolder.held = holderUnderTest.get();
                } catch (TimeoutException e) { }
                synchronized(QueuedObjectHolderBehaviour.this) {
                    QueuedObjectHolderBehaviour.this.notifyAll();
                }
            }
        });
        
        threadForGetToRun.start();
        
        // Add an item to the holder. Wait for thread to initialise and
        // start getting things.
        synchronized (this) {
            try {
                wait(200); 
            } catch (InterruptedException e) {};
            
            holderUnderTest.set("Frodo");
            try {
                while (threadForGetToRun.isAlive()) { wait(200); }
            } catch (InterruptedException e) {}
        }
        
        ensureThat(ourHolder.held, eq("Frodo"));
    }
    

    public void shouldTimeoutIfTooLate() {
        PseudoClock clock = new PseudoClock();
        
        final QueuedObjectHolder holderUnderTest = 
            new QueuedObjectHolder(new ClockedTimeouterFactory(clock), 100);
        final ObjectHolder ourHolder = new ObjectHolder();
        
        Thread threadForGetToRun = new Thread(new Runnable() {
            public void run() {
                try  {
                    holderUnderTest.get();
                } catch (TimeoutException e) {
                    ourHolder.held = e;
                }
                synchronized(QueuedObjectHolderBehaviour.this) {
                    QueuedObjectHolderBehaviour.this.notifyAll();
                }
            }
        });
        
        threadForGetToRun.start();          
        
        synchronized (this) {
            // Wait for a short while to let the thread run
            try {
                wait(100); 
            } catch (InterruptedException e) {};
        }
        

        clock.setTimeInMillis(clock.getTimeInMillis() + 2000);           

        synchronized (this) {
            // Wait for a short while to let the thread throw exception
            try {
                while (threadForGetToRun.isAlive()) { wait(100); }
            } catch (InterruptedException e) {};
        }
                        
        ensureThat(ourHolder.held, isA(TimeoutException.class));
    }    
    
    private static class ObjectHolder {
        private Object held;
    }
}
