package com.thoughtworks.jbehave.extensions.harness.component;

import java.awt.Component;
import java.awt.Container;
import java.util.HashSet;
import java.util.Set;

public class ComponentFinder {

    public Component findExactComponent(Container container, ComponentFilter filter) {
        Component[] matchedComponents = findComponents(container, filter);
        if (matchedComponents.length == 0) {
            throw new ComponentFinderException("No matching component found");
        }
        if (matchedComponents.length > 1) {
            throw new ComponentFinderException("More than one matching component found");
        }
        return matchedComponents[0];
    }
    
    public Component[] findComponents(Container container, ComponentFilter filter) {
        Set matchedSet = new HashSet();
        addMatchingComponentsToSet(container, filter, matchedSet);
        return (Component[])matchedSet.toArray(new Component[matchedSet.size()]);
    }
    
    private void addMatchingComponentsToSet(Container container, ComponentFilter filter, Set matchedSet) {
        int numberOfChildren = container.getComponentCount();
        for (int i = 0; i < numberOfChildren; i++) {
            Component child = container.getComponent(i);
            if (filter.matches(child)) matchedSet.add(child);
            if (child instanceof Container) {
                addMatchingComponentsToSet((Container)child, filter, matchedSet);
            }
        }
    }
}
