package org.jbehave.core.story.domain;

import org.jbehave.core.story.renderer.Renderer;

public abstract class AbstractStep implements Step, HasCleanUp {

    protected final ScenarioComponent component;

    public abstract void perform(World world);

    public AbstractStep(ScenarioComponent component) {
        this.component = component;
    }

    public void narrateTo(Renderer renderer) {
        component.narrateTo(renderer);
    }

    public boolean containsMocks() {
        return component.containsMocks();
    }

    public void verifyMocks() {
        component.verifyMocks();
    }
    
    public void cleanUp(World world) {
        if (component instanceof HasCleanUp) {
            ((HasCleanUp) component).cleanUp(world);
        }
    }
}