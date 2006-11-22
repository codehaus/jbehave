package jbehave.core.story.domain;

public interface Step extends ScenarioComponent {

    void perform(World world);
    void undoIn(World world);
}
