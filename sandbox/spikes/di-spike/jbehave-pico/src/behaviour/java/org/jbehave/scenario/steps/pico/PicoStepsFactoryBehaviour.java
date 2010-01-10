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

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PicoStepsFactoryBehaviour {

    private static Field instMethod;

    @Before
    public void steup() throws NoSuchFieldException {
        instMethod = Steps.class.getDeclaredField("instance");
        instMethod.setAccessible(true);
    }


    @Test
    public void ensureThatContainerCanBeUsedToMakeSteps() throws NoSuchFieldException, IllegalAccessException {
        MutablePicoContainer parent = new DefaultPicoContainer(new Caching().wrap(new ConstructorInjection()));
        parent.as(Characteristics.USE_NAMES).addComponent(FooSteps.class);

        CandidateSteps[] foo = new PicoStepsFactory(new StepsConfiguration(), parent).createCandidateSteps();
        assertOneFooStepsComponent(foo);
    }

    @Test
    public void ensureThatContainerCanBeUsedToMakeStepsWithACtorDep() throws NoSuchFieldException, IllegalAccessException {
        MutablePicoContainer parent = new DefaultPicoContainer(new Caching().wrap(new ConstructorInjection()));
        parent.as(Characteristics.USE_NAMES).addComponent(ExtendedFooSteps.class);
        parent.addComponent(Integer.class, 42);

        CandidateSteps[] foo = new PicoStepsFactory(new StepsConfiguration(), parent).createCandidateSteps();
        assertOneFooStepsComponent(foo);
        assertEquals(42, (int) ((ExtendedFooSteps) instMethod.get(foo[0])).num);
    }

    private void assertOneFooStepsComponent(CandidateSteps[] foo) throws NoSuchFieldException, IllegalAccessException {
        assertEquals(1, foo.length);
        assertTrue(foo[0] instanceof Steps);
        Object instance = instMethod.get(foo[0]);
        assertTrue(instance instanceof FooSteps);
    }

    public static class ExtendedFooSteps extends FooSteps {
        private final Integer num;

        public ExtendedFooSteps(Integer foo) {
            this.num = foo;
        }
    }

    @Test
    public void ensureThatMissingDepsMakeItBarf() throws NoSuchFieldException, IllegalAccessException {
        MutablePicoContainer parent = new DefaultPicoContainer(new Caching().wrap(new ConstructorInjection()));
        parent.as(Characteristics.USE_NAMES).addComponent(ExtendedFooSteps.class);

        CandidateSteps[] foo = new CandidateSteps[0];
        try {
            foo = new PicoStepsFactory(new StepsConfiguration(), parent).createCandidateSteps();
            fail("should have barfed");
        } catch (AbstractInjector.UnsatisfiableDependenciesException e) {
            // expected
        }
    }

    public static class FooSteps {

        @Given("a trader of name %trader")
        public void aTrader(String trader) {
        }

    }


}
