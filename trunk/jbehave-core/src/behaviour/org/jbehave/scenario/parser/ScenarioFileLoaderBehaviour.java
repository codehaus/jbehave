package org.jbehave.scenario.parser;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStream;

import org.jbehave.scenario.Scenario;
import org.jbehave.scenario.parser.scenarios.MyPendingScenario;
import org.junit.Test;

public class ScenarioFileLoaderBehaviour {

	@Test
    public void canLoadScenario() {
    	ScenarioParser parser = mock(ScenarioParser.class);
        ScenarioFileLoader loader = new ScenarioFileLoader(parser);
        loader.loadScenarioDefinitionsFor(MyPendingScenario.class);
        verify(parser).defineStoryFrom("Given my scenario");
    }

    @Test
    public void canLoadScenarioWithCustomFilenameResolver() {
    	ScenarioParser parser = mock(ScenarioParser.class);
        ScenarioFileLoader loader = new ScenarioFileLoader(new CasePreservingResolver(".scenario"), parser);
        loader.loadScenarioDefinitionsFor(MyPendingScenario.class);
        verify(parser).defineStoryFrom("Given my scenario");
    }
    
    @Test(expected = ScenarioNotFoundException.class)
    public void cannotLoadScenarioForInexistentResource() {
        ScenarioFileLoader loader = new ScenarioFileLoader();
        loader.loadScenarioDefinitionsFor(InexistentScenario.class);
    }

    @Test(expected = InvalidScenarioResourceException.class)
    public void cannotLoadScenarioForInvalidResource() {
        ScenarioFileLoader loader = new ScenarioFileLoader(new InvalidClassLoader());
        loader.loadScenarioDefinitionsFor(MyPendingScenario.class);
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
