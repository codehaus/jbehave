package com.sirenian.hellbound.util;

public interface ErrorHandler {

    ErrorHandler NULL = new ErrorHandler() {
        public void handle(Throwable t) {}
    };

    void handle(Throwable t);
    
}
