package jbehave.core.story.domain;

import jbehave.core.mock.Mock;

public class GivensBehaviour extends ScenarioComponentsBehaviour {

  protected ScenarioComponents newComposite(ScenarioComponent component1, ScenarioComponent component2) {
    return new Givens((Given) component1, (Given) component2);
  }

  protected Class componentType() {
    return Given.class;
  }

  public void shouldTellComponentsToSetUpWorld() throws Exception {
    // given...
    Mock component1 = mockComponent("component1");
    Mock component2 = mockComponent("component2");
    World world = (World) stub(World.class);
    Given givens = new Givens((Given) component1, (Given) component2);

    // expect...
    component1.expects("setUp").with(world);
    component2.expects("setUp").with(world).after(component1, "setUp");

    // when...
    givens.setUp(world);
    
    // then...
    verifyMocks();
  }
  
  public void shouldTidyUpComponentsInReverseOrder() {
      // given...
      Mock component1 = mockComponent("component1");
      Mock component2 = mockComponent("component2");
      World world = (World) stub(World.class);
      Given givens = new Givens((Given) component1, (Given) component2);

      // expect...
      component2.expects("tidyUp").with(world);
      component1.expects("tidyUp").with(world).after(component2, "tidyUp");

      // when...
      givens.tidyUp(world);
      
      // then...
      verifyMocks();      
  }
}
