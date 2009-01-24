package org.jbehave.scenario.errors;


public interface PendingErrorStrategy extends ErrorStrategy {

    PendingErrorStrategy PASSING = new PendingErrorStrategy() { 
        public void handleError(Throwable throwable) {}
    };
    
    PendingErrorStrategy FAILING = new PendingErrorStrategy() {
        public void handleError(Throwable throwable) throws Throwable {
            throw throwable;
        } 
    };
}
