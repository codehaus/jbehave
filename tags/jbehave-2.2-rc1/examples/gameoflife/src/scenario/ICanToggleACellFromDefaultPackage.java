import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.JUnitScenario;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;

import com.lunivore.gameoflife.steps.GridSteps;

public class ICanToggleACellFromDefaultPackage extends JUnitScenario {

    public ICanToggleACellFromDefaultPackage() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public ICanToggleACellFromDefaultPackage(final ClassLoader classLoader) {
        super(new PropertyBasedConfiguration() {
            @Override
            public ClasspathScenarioDefiner forDefiningScenarios() {
                return new ClasspathScenarioDefiner(new UnderscoredCamelCaseResolver(), new PatternScenarioParser(this),
                        classLoader);
            }
        });
        addSteps(new GridSteps());
    }
}
