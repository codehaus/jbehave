package org.jbehave.scenario.steps.pico;

import org.jbehave.scenario.steps.StepsConfiguration;
import org.picocontainer.PicoContainer;

public class PicoEnabledStepsConfiguration extends StepsConfiguration {
    private final PicoContainer parent;

    public PicoEnabledStepsConfiguration(PicoContainer parent) {
        this.parent = parent;
    }

    public PicoContainer getParentContainer() {
        return parent;
    }
}
