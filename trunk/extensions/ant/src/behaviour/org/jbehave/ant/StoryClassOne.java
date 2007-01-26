package org.jbehave.ant;

import org.jbehave.core.listener.BehaviourListener;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.Story;
import org.jbehave.core.story.renderer.Renderer;

public class StoryClassOne implements Story {

    private boolean wasRun;
    private boolean wasNarrated;

    public void addListener(BehaviourListener listener) {}

    public Narrative narrative() {
        return new Narrative("", "", "");
    }

    public void run() {
        wasRun = true;
    }

    public void specify() {}

    public void narrateTo(Renderer renderer) {
        wasNarrated = true;
    }

    public boolean wasNarrated() {
        return wasNarrated;
    }

    public boolean wasRun() {
        return wasRun;
    }

}
