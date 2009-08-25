/*
 * Created on 29-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package jbehave.core.listener;

import java.io.Writer;

import jbehave.core.util.Timer;


/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class PlainTextMethodListener extends PlainTextListener {
    public PlainTextMethodListener(Writer writer, Timer timer) {
        super(writer, timer);
    }
    
    public PlainTextMethodListener(Writer writer) {
        this(writer, new Timer());
    }
}
