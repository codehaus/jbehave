/*
 * Created on 29-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package com.thoughtworks.jbehave.core.util;

import com.thoughtworks.jbehave.core.Verify;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:geoff@jbehave.org">Geoff Oliphant</a>
 */
public class TimerBehaviour {
    public void shouldMeasureAPeriodOfTime() throws Exception {
        // setup
        Timer timer = new Timer();
        timer.start();
        
        // execute
        // TODO make this not time-dependent - could possibly fail
        Thread.sleep(10);
        timer.stop();
        
        // verify
        Verify.that(timer.elapsedTimeMillis() > 0);
    }
}
