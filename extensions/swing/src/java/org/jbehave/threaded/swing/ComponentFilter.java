package org.jbehave.threaded.swing;

import java.awt.Component;

public interface ComponentFilter {

    boolean matches(Component child);
    
}
