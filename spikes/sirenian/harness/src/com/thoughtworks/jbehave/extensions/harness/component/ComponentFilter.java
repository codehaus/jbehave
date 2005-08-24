package com.thoughtworks.jbehave.extensions.harness.component;

import java.awt.Component;

public interface ComponentFilter {

    boolean matches(Component child);
    
}
