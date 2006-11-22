package jbehave.core.story.domain;

import jbehave.core.story.renderer.Renderer;

public class ScenarioComponents implements ScenarioComponent {
	private final ScenarioComponent[] components;
    
    public ScenarioComponents(ScenarioComponent[] components) {
        this.components = components;
    }
    
    
	public void narrateTo(Renderer renderer) {
        for (int i = 0; i < components.length; i++) {
            components[i].narrateTo(renderer);
        }
	}

    public boolean containsMocks() {
        for (int i = 0; i < components.length; i++) {
            if (components[i].containsMocks()) {
                return true;
            }
        }
        return false;
    }

    public void verifyMocks() {
        for (int i = 0; i < components.length; i++) {
            components[i].verifyMocks();
        }
    }
}
