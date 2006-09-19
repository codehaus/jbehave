package jbehave.extensions.threaded.component;

import java.awt.Component;

public interface ComponentFilter {

    boolean matches(Component child);
    
}
