package org.jbehave.core.story;

import java.text.MessageFormat;
import java.util.Iterator;

import org.jbehave.core.story.codegen.domain.BasicDetails;
import org.jbehave.core.story.codegen.domain.ScenarioDetails;
import org.jbehave.core.story.codegen.domain.StoryDetails;
import org.jbehave.core.story.domain.Event;
import org.jbehave.core.story.domain.Given;
import org.jbehave.core.story.domain.MultiStepScenario;
import org.jbehave.core.story.domain.Narrative;
import org.jbehave.core.story.domain.Outcome;
import org.jbehave.core.story.domain.Scenario;
import org.jbehave.core.story.domain.ScenarioDrivenStory;
import org.jbehave.core.story.domain.Story;
import org.jbehave.core.util.CamelCaseConverter;


/**
 * Builds a Story from the StoryDetails.  
 * Typically used to load a story when used in conjuction with the StoryParser.
 * 
 * @author Mauro Talevi
 */
public class StoryBuilder {

    private StoryDetails details;
    private final String rootPackageName;
    private ClassLoader classLoader;
    private final ClassBuilder builder = new ClassBuilder();

    public StoryBuilder(StoryDetails details, String rootPackageName) {
        this(details, rootPackageName, Thread.currentThread().getContextClassLoader());
    }    
    
    public StoryBuilder(StoryDetails details, String rootPackageName, ClassLoader classLoader) {
        this.details = details;
        this.rootPackageName = rootPackageName;
        this.classLoader = classLoader;
        }    

    public Story story(){
        ScenarioDrivenStory story = new ScenarioDrivenStory(new Narrative(details.role, details.feature, details.benefit)) {
            public void specify() {
                for ( Iterator i = details.scenarios.iterator(); i.hasNext(); ){
                    addScenario(scenario((ScenarioDetails)i.next(), details.name));
                }
            }
        };
        return story;        
    }

    private Scenario scenario(final ScenarioDetails details, String storyName) {
        return new MultiStepScenario() {
            public void specify() {
                // given
                for (Iterator i = details.context.givens.iterator(); i.hasNext();) {
                    BasicDetails given = (BasicDetails)i.next();
                    given((Given)builder.newGiven(given.name));
                }
                
                // when
                when((Event)builder.newEvent(details.event.name));
                
                // then
                for (Iterator i = details.outcome.outcomes.iterator(); i.hasNext();) {
                    BasicDetails outcome = (BasicDetails)i.next();
                    then((Outcome)builder.newOutcome(outcome.name));
                }
            }
        };
    }
    
    private final class ClassBuilder {
        private static final String CLASS_NAME_TEMPLATE = "{0}.{1}.{2}";
        private static final String GIVENS = "givens";
        private static final String EVENTS = "events";
        private static final String OUTCOMES = "outcomes";

        public Object newGiven(String name) {
            return newInstance(name, GIVENS);
        }
        public Object newEvent(String name) {
            return newInstance(name, EVENTS);
        }
        public Object newOutcome(String name) {
            return newInstance(name, OUTCOMES);
        }
        
        private String toCamelCase(String name) {
            return new CamelCaseConverter(name).toCamelCase();
        }
        
        private Object newInstance(String name, String packageName) {
            try {
                String fullName = buildFullClassName(name, packageName);
                return classLoader.loadClass(name).newInstance();
            } catch ( Exception e) {
                throw new RuntimeException("Failed to create instance for name "+name, e);
            }
        }
        
        private String buildFullClassName(String name, String packageName) {
            return MessageFormat.format(CLASS_NAME_TEMPLATE, new Object[]{rootPackageName, packageName, toCamelCase(name)});
        }
    }   
}
