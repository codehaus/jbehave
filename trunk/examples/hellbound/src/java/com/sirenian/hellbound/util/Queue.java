package com.sirenian.hellbound.util;

public interface Queue {

    void invokeAndWait(Runnable empty_runnable);
    
    void stop();
}
