package org.jbehave.ant;

import org.jbehave.core.exception.VerificationException;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.ScenarioDrivenStory;

public class FailingStoryClass extends ScenarioDrivenStory {

    public FailingStoryClass() {
        super(new Narrative("", "", ""));
    }

    public void specify() {
        
    }

    public void run() {
        throw new VerificationException("Failed");
    }
}
