package org.jbehave.it;

import org.jbehave.core.story.renderer.ConsolePlainTextRenderer;

/**
 * @author Mauro Talevi
 */
public class ItRenderer extends ConsolePlainTextRenderer {
    
    public ItRenderer() {
        super();
        System.out.println("Using ItRenderer");
    }
        
}
