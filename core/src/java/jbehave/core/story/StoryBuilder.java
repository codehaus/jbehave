package jbehave.core.story;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jbehave.core.story.codegen.domain.BasicDetails;
import jbehave.core.story.codegen.domain.ContextDetails;
import jbehave.core.story.codegen.domain.OutcomeDetails;
import jbehave.core.story.codegen.domain.ScenarioDetails;
import jbehave.core.story.codegen.domain.StoryDetails;
import jbehave.core.story.domain.Event;
import jbehave.core.story.domain.Events;
import jbehave.core.story.domain.Given;
import jbehave.core.story.domain.Givens;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Outcome;
import jbehave.core.story.domain.Outcomes;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.ScenarioDrivenStory;
import jbehave.core.story.domain.ScenarioUsingMiniMock;
import jbehave.core.story.domain.Story;
import jbehave.core.util.CamelCaseConverter;

/**
 * Builds a Story from the StoryDetails.  
 * Typically used to load a story when used in conjuction with the StoryParser.
 * 
 * @author Mauro Talevi
 */
public class StoryBuilder {

    private StoryDetails details;
    private ClassLoader classLoader;
    private ClassNameBuilder classNameBuilder;

    public StoryBuilder(StoryDetails details, String rootPackageName) {
        this(details, rootPackageName, Thread.currentThread().getContextClassLoader());
    }    
    
    public StoryBuilder(StoryDetails details, String rootPackageName, ClassLoader classLaoder) {
        this.details = details;
        this.classLoader = classLaoder;
        this.classNameBuilder = new ClassNameBuilder(rootPackageName);
    }    

    public Story story(){
        ScenarioDrivenStory story = new ScenarioDrivenStory(new Narrative(details.role, details.feature, details.benefit));
        for ( Iterator i = details.scenarios.iterator(); i.hasNext(); ){
            story.addScenario(scenario((ScenarioDetails)i.next(), details.name));
        }
        return story;        
    }

    private Scenario scenario(ScenarioDetails details, String storyName) {
        return new ScenarioUsingMiniMock(givens(details.context), whens(details.event), thens(details.outcome));
    }

    private Given givens(ContextDetails context) {
        List givens = new ArrayList();
        for ( Iterator i = context.givens.iterator(); i.hasNext(); ){
            givens.add((Given)newInstance(classNameBuilder.givenName((String) i.next())));
        }        
        return new Givens((Given[]) givens.toArray(new Given[givens.size()]));
    }

    private Event whens(BasicDetails event) {
        return new Events((Event)newInstance(classNameBuilder.eventName(event.name)));
    }

    private Outcome thens(OutcomeDetails outcome) {
        List outcomes = new ArrayList();
        for ( Iterator i = outcome.outcomes.iterator(); i.hasNext(); ){
            outcomes.add((Outcome)newInstance(classNameBuilder.outcomeName((String) i.next())));
        }        
        return new Outcomes((Outcome[]) outcomes.toArray(new Outcome[outcomes.size()]));
    }

    private Object newInstance(String name) {
        try {
            return classLoader.loadClass(name).newInstance();
        } catch ( Exception e) {
            throw new RuntimeException("Failed to create instance for name "+name, e);
        }
    }
    
    private static final class ClassNameBuilder {
        private static final String CLASS_NAME_TEMPLATE = "{0}.{1}.{2}";
        private static final String GIVENS = "givens";
        private static final String EVENTS = "events";
        private static final String OUTCOMES = "outcomes";
        private final String root;

        public ClassNameBuilder(final String root) {
            this.root = root;
        }

        public String givenName(String name){
            return MessageFormat.format(CLASS_NAME_TEMPLATE, new Object[]{root, GIVENS, toCamelCase(name)});
        }

        public String eventName(String name){
            return MessageFormat.format(CLASS_NAME_TEMPLATE, new Object[]{root, EVENTS, toCamelCase(name)});
        }
        public String outcomeName(String name){
            return MessageFormat.format(CLASS_NAME_TEMPLATE, new Object[]{root, OUTCOMES, toCamelCase(name)});
        }

        private String toCamelCase(String name) {
            return new CamelCaseConverter(name).toCamelCase();
        }
    }   


}
