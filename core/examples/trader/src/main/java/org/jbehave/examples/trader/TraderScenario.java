package org.jbehave.examples.trader;

import static org.jbehave.scenario.reporters.ScenarioReporterBuilder.Format.CONSOLE;
import static org.jbehave.scenario.reporters.ScenarioReporterBuilder.Format.HTML;
import static org.jbehave.scenario.reporters.ScenarioReporterBuilder.Format.TXT;
import static org.jbehave.scenario.reporters.ScenarioReporterBuilder.Format.XML;

import org.jbehave.scenario.JUnitScenario;
import org.jbehave.scenario.PropertyBasedConfiguration;
import org.jbehave.scenario.RunnableScenario;
import org.jbehave.scenario.parser.ClasspathScenarioDefiner;
import org.jbehave.scenario.parser.PatternScenarioParser;
import org.jbehave.scenario.parser.ScenarioDefiner;
import org.jbehave.scenario.parser.ScenarioNameResolver;
import org.jbehave.scenario.parser.UnderscoredCamelCaseResolver;
import org.jbehave.scenario.reporters.FilePrintStreamFactory;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.reporters.ScenarioReporterBuilder;

public class TraderScenario extends JUnitScenario {

    private static ScenarioNameResolver converter = new UnderscoredCamelCaseResolver(".scenario");

    public TraderScenario(final Class<? extends RunnableScenario> scenarioClass) {
        super(new PropertyBasedConfiguration() {
            @Override
            public ScenarioDefiner forDefiningScenarios() {
                return new ClasspathScenarioDefiner(converter, new PatternScenarioParser(keywords()));
            }

            @Override
            public ScenarioReporter forReportingScenarios() {
                return new ScenarioReporterBuilder(new FilePrintStreamFactory(scenarioClass, converter))
                            .with(CONSOLE)
                            .with(TXT)
                            .with(HTML)
                            .with(XML)
                            .build();
            }

        }, new TraderSteps());
    }

}
