package jbehave.core.story;

import java.text.MessageFormat;
import java.util.Iterator;

import jbehave.core.story.codegen.domain.ScenarioDetails;
import jbehave.core.story.codegen.domain.StoryDetails;
import jbehave.core.story.domain.MultiStepScenario;
import jbehave.core.story.domain.Narrative;
import jbehave.core.story.domain.Scenario;
import jbehave.core.story.domain.ScenarioDrivenStory;
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

    public StoryBuilder(StoryDetails details, String rootPackageName) {
        this(details, rootPackageName, Thread.currentThread().getContextClassLoader());
    }    
    
    public StoryBuilder(StoryDetails details, String rootPackageName, ClassLoader classLaoder) {
        this.details = details;
        this.classLoader = classLaoder;
    }    

    public Story story(){
        ScenarioDrivenStory story = new ScenarioDrivenStory(new Narrative(details.role, details.feature, details.benefit));
        for ( Iterator i = details.scenarios.iterator(); i.hasNext(); ){
            story.addScenario(scenario((ScenarioDetails)i.next(), details.name));
        }
        return story;        
    }

    private Scenario scenario(final ScenarioDetails details, String storyName) {
        return new MultiStepScenario() {
            public void assemble() {
                // TODO fix ScenarioDetails to have arbitrary steps
            }
        };
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
