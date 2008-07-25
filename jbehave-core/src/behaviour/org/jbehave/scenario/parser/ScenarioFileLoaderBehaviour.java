package org.jbehave.scenario.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.io.IOException;
import java.io.InputStream;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.scenarios.MyPendingScenario;
import org.junit.Test;

public class ScenarioFileLoaderBehaviour {

    @Test
    public void canLoadScenario() {
        ScenarioFileLoader loader = new ScenarioFileLoader();
        ensureThat(loader.loadStepsFor(MyPendingScenario.class), equalTo("Given my scenario"));
    }

    @Test
    public void canLoadScenarioWithCustomFilenameResolver() {
        ScenarioFileLoader loader = new ScenarioFileLoader(new CasePreservingResolver(".scenario"));
        ensureThat(loader.loadStepsFor(MyPendingScenario.class), equalTo("Given my scenario"));
    }
    
    @Test(expected = ScenarioNotFoundException.class)
    public void cannotLoadScenarioForInexistentResource() {
        ScenarioFileLoader loader = new ScenarioFileLoader();
        loader.loadStepsFor(InexistentScenario.class);
    }

    @Test(expected = InvalidScenarioResourceException.class)
    public void cannotLoadScenarioForInvalidResource() {
        ScenarioFileLoader loader = new ScenarioFileLoader(new InvalidClassLoader());
        loader.loadStepsFor(MyPendingScenario.class);
    }

    static class InexistentScenario extends Scenario {

    }

    static class InvalidClassLoader extends ClassLoader {

        @Override
        public InputStream getResourceAsStream(String name) {
            return new InputStream() {

                public int available() throws IOException {
                    return 1;
                }

                @Override
                public int read() throws IOException {
                    throw new IOException("invalid");
                }

            };
        }

    }

}
