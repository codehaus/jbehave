package org.jbehave.scenario.parser;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;

import java.io.IOException;
import java.io.InputStream;

import org.jbehave.scenario.Scenario;
import org.junit.Test;

public class ScenarioFileLoaderBehaviour {

    @Test
    public void canLoadScenario() {
        ScenarioFileLoader loader = new ScenarioFileLoader();
        ensureThat(loader.loadScenarioAsString(MyScenario.class), equalTo("Given my scenario"));

    }

    @Test(expected = ScenarioNotFoundException.class)
    public void cannotLoadScenarioForInexistentResource() {
        ScenarioFileLoader loader = new ScenarioFileLoader();
        loader.loadScenarioAsString(InexistentScenario.class);
    }

    @Test(expected = InvalidScenarioResourceException.class)
    public void cannotLoadScenarioForInvalidResource() {
        ScenarioFileLoader loader = new ScenarioFileLoader(new InvalidClassLoader());
        loader.loadScenarioAsString(MyScenario.class);
    }

    static class MyScenario extends Scenario {

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
