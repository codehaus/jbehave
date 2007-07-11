package org.jbehave.core.story.codegen.velocity;

import java.io.File;

import org.jbehave.core.mock.UsingMatchers;
import org.jbehave.core.story.codegen.domain.ScenarioDetails;
import org.jbehave.core.story.codegen.domain.StoryDetails;

/**
 * 
 * @author Mauro Talevi
 */
public class VelocityCodeGeneratorBehaviour extends UsingMatchers {

    public void shouldGenerateCodeForStoryWithFullScenario() throws Exception {
        // given
        StoryDetails storyDetails = new StoryDetails("Joe drinks vodka", "", "", "");
        ScenarioDetails scenario1 = new ScenarioDetails();
        scenario1.name = "Happy path";
        scenario1.context.givens.add("a bar downtown");
        scenario1.context.givens.add("a thirsty Joe");
        scenario1.event.name = "Joe asks for a Smirnov";
        scenario1.outcome.outcomes.add("bartender serves Joe");
        scenario1.outcome.outcomes.add("Joe is happy");
        storyDetails.addScenario(scenario1);
        ScenarioDetails scenario2 = new ScenarioDetails();
        scenario2.name = "Unhappy path";
        scenario2.context.givens.add("a pub uptown");
        scenario2.context.givens.add("an equally thirsty Joe");
        scenario2.event.name = "Joe asks for an Absolut";
        scenario2.outcome.outcomes.add("bartender tells Joe it is sold out");
        scenario2.outcome.outcomes.add("Joe is unhappy");
        storyDetails.addScenario(scenario2);

        // when
        String generatedSourceDir = "delete_me/generated-src";
        VelocityCodeGenerator generator = new VelocityCodeGenerator(generatedSourceDir,
                "generated.stories");
        generator.generateStory(storyDetails);

        // then
        String[] generatedPaths = new String[]{
           "events/JoeAsksForASmirnov.java",      
           "events/JoeAsksForAnAbsolut.java",      
           "givens/ABarDowntown.java",      
           "givens/APubUptown.java",      
           "givens/AThirstyJoe.java",      
           "givens/AnEquallyThirstyJoe.java",      
           "outcomes/BartenderServesJoe.java",      
           "outcomes/BartenderTellsJoeItIsSoldOut.java",      
           "outcomes/JoeIsHappy.java",      
           "outcomes/JoeIsUnhappy.java"
        };
        
        for ( int i = 0; i < generatedPaths.length; i++ ){
            ensureThat(new File(generatedSourceDir+File.separator+generatedPaths[i]).exists() );           
        }
    }

}
