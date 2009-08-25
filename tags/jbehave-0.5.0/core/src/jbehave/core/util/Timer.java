/*
 * Created on 28-Jan-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.core.util;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 * @author <a href="mailto:geoff@jbehave.org">Geoff Oliphant</a>
 */
public class Timer {
    long start = -1L;
    long elapsed = 0L;

    public void start() {
        start = System.currentTimeMillis();
    }

    public void stop() {
        elapsed = System.currentTimeMillis() - start;
    }

    public long elapsedTimeMillis() {
        return elapsed;
    }
}
