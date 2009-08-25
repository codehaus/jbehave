/*
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package org.jbehave.core.story.renderer;

/**
 * @author Mauro Talevi
 */
public class ConsolePlainTextRenderer extends PlainTextRenderer {
    
    public ConsolePlainTextRenderer() {
        super(System.out);
    }
        
}
