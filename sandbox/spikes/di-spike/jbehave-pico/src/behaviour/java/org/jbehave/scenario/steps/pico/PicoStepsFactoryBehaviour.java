package org.jbehave.scenario.steps.pico;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.Steps;
import org.jbehave.scenario.steps.StepsConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.injectors.AbstractInjector;
import org.picocontainer.injectors.ConstructorInjection;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PicoStepsFactoryBehaviour {

    private static Field stepsInstance;

    @Before
    public void setUp() throws NoSuchFieldException {
        stepsInstance = Steps.class.getDeclaredField("instance");
        stepsInstance.setAccessible(true);
    }


    @Test
    public void ensureThatStepsCanBeCreated() throws NoSuchFieldException, IllegalAccessException {
        MutablePicoContainer parent = new DefaultPicoContainer(new Caching().wrap(new ConstructorInjection()));
        parent.as(Characteristics.USE_NAMES).addComponent(FooSteps.class);

        CandidateSteps[] steps = new PicoStepsFactory(new StepsConfiguration(), parent).createCandidateSteps();
        assertFooStepsFound(steps);
    }

    @Test
    public void ensureThatStepsWithConstructorDependencyCanBeCreated() throws NoSuchFieldException, IllegalAccessException {
        MutablePicoContainer parent = new DefaultPicoContainer(new Caching().wrap(new ConstructorInjection()));
        parent.as(Characteristics.USE_NAMES).addComponent(FooStepsWithDependency.class);
        parent.addComponent(Integer.class, 42);

        CandidateSteps[] steps = new PicoStepsFactory(new StepsConfiguration(), parent).createCandidateSteps();
        assertFooStepsFound(steps);
        assertEquals(42, (int) ((FooStepsWithDependency) stepsInstance.get(steps[0])).integer);
    }

    private void assertFooStepsFound(CandidateSteps[] steps) throws NoSuchFieldException, IllegalAccessException {
        assertEquals(1, steps.length);
        assertTrue(steps[0] instanceof Steps);
        Object instance = stepsInstance.get(steps[0]);
        assertTrue(instance instanceof FooSteps);
    }


    @Test(expected=AbstractInjector.UnsatisfiableDependenciesException.class)
    public void ensureThatStepsWithMissingDependenciesCannotBeCreated() throws NoSuchFieldException, IllegalAccessException {
        MutablePicoContainer parent = new DefaultPicoContainer(new Caching().wrap(new ConstructorInjection()));
        parent.as(Characteristics.USE_NAMES).addComponent(FooStepsWithDependency.class);

        new PicoStepsFactory(new StepsConfiguration(), parent).createCandidateSteps();
    }

    public static class FooSteps {

        @Given("a trader of name %trader")
        public void aTrader(String trader) {
        }

    }

    public static class FooStepsWithDependency extends FooSteps {
        private final Integer integer;

        public FooStepsWithDependency(Integer steps) {
            this.integer = steps;
        }

    }
}
