package org.jbehave.scenario.steps.pico;

import org.jbehave.scenario.annotations.Given;
import org.jbehave.scenario.annotations.When;
import org.jbehave.scenario.parser.PrefixCapturingPatternBuilder;
import org.jbehave.scenario.parser.StepPatternBuilder;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.jbehave.scenario.steps.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.containers.EmptyPicoContainer;
import org.picocontainer.injectors.ConstructorInjection;

import javax.inject.Named;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.jbehave.Ensure.ensureThat;
import static org.jbehave.Ensure.not;
import static org.jbehave.scenario.steps.StepType.GIVEN;
import static org.jbehave.scenario.steps.StepType.THEN;
import static org.jbehave.scenario.steps.StepType.WHEN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PicoEnabledCandidateStepBehaviour {

    private static final StepPatternBuilder PATTERN_BUILDER = new PrefixCapturingPatternBuilder();
    private static final String NL = System.getProperty("line.separator");
	private Map<String, String> tableRow = new HashMap<String, String>();
	private Map<StepType, String> startingWords = startingWords();
    private MutablePicoContainer parent;

	private Map<StepType, String> startingWords() {
		Map<StepType, String> map = new HashMap<StepType, String>();
		map.put(GIVEN, "Given");
		map.put(WHEN, "When");
		map.put(THEN, "Then");
		return map;
	}

    @Before
    public void setup() {
        parent = new DefaultPicoContainer(new Caching().wrap(new ConstructorInjection()));
    }


    @Test
    public void shouldMatchASimpleString() throws Exception {
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("I laugh", GIVEN, SomeSteps2.class.getMethod("aMethod"),
                null, PATTERN_BUILDER, new ParameterConverters(), startingWords, new EmptyPicoContainer());
        ensureThat(candidateStep.matches("Given I laugh"));
    }

    @Test
    public void shouldMatchAStringWithArguments() throws Exception {
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("windows on the $nth floor", WHEN, SomeSteps2.class
				        .getMethod("aMethod"), null, PATTERN_BUILDER, new ParameterConverters(), startingWords, new EmptyPicoContainer());
        ensureThat(candidateStep.matches("When windows on the 1st floor"));
        ensureThat(not(candidateStep.matches("When windows on the 1st floor are open")));
    }

    @Test
    public void shouldProvideARealStepUsingTheMatchedString() throws Exception {
        parent.as(Characteristics.USE_NAMES).addComponent(SomeSteps2.class);
        SomeSteps2 someSteps = parent.getComponent(SomeSteps2.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("I live on the $args floor", THEN, SomeSteps2.class.getMethod(
				        "aMethodWith", String.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        Step step = candidateStep.createFrom(tableRow, "Then I live on the 1st floor");
        step.perform();
        ensureThat((String) someSteps.args, equalTo("1st"));
    }

    @Test
    public void shouldMatchMultilineStrings() throws Exception {
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("the grid should look like $grid", THEN, SomeSteps2.class
				        .getMethod("aMethod"), null, PATTERN_BUILDER, new ParameterConverters(), startingWords, new EmptyPicoContainer());
        ensureThat(candidateStep.matches("Then the grid should look like " + NL + "...." + NL + "...." + NL));
    }

    @Test
    public void shouldConvertArgsToAppropriateNumbers() throws Exception {
        parent.as(Characteristics.USE_NAMES).addComponent(SomeSteps2.class);
        SomeSteps2 someSteps = parent.getComponent(SomeSteps2.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("I should live in no. $args", THEN, SomeSteps2.class.getMethod(
				        "aMethodWith", int.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "Then I should live in no. 14").perform();
        ensureThat((Integer) someSteps.args, equalTo(14));

        candidateStep = new PicoEnabledCandidateStep("I should live in no. $args", THEN, SomeSteps2.class.getMethod("aMethodWith",
				        long.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "Then I should live in no. 14").perform();
        ensureThat((Long) someSteps.args, equalTo(14L));

        candidateStep = new PicoEnabledCandidateStep("I should live in no. $args", THEN, SomeSteps2.class.getMethod("aMethodWith",
				        double.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "Then I should live in no. 14").perform();
        ensureThat((Double) someSteps.args, equalTo(14.0));

        candidateStep = new PicoEnabledCandidateStep("I should live in no. $args", THEN, SomeSteps2.class.getMethod("aMethodWith",
				        float.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "Then I should live in no. 14").perform();
        ensureThat((Float) someSteps.args, equalTo(14.0f));
    }

    @Test
    public void shouldProvideAStepWithADescriptionThatMatchesTheCandidateStep() throws Exception {
        ScenarioReporter reporter = mock(ScenarioReporter.class);
        parent.as(Characteristics.USE_NAMES).addComponent(SomeSteps2.class);
        SomeSteps2 someSteps = parent.getComponent(SomeSteps2.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("I live on the $nth floor", THEN, SomeSteps2.class.getMethod(
				        "aMethodWith", String.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        Step step = candidateStep.createFrom(tableRow, "Then I live on the 1st floor");

        StepResult result = step.perform();
        result.describeTo(reporter);
        verify(reporter).successful("Then I live on the 1st floor");
    }

    @Test
    @Ignore()    
    public void shouldNotFailJustBecauseWeHaveDifferentNewlinesToTheOneTheScenarioWasWrittenIn() throws Exception {
        String windowsNewline = "\r\n";
        String unixNewline = "\n";
        String systemNewline = System.getProperty("line.separator");
        parent.as(Characteristics.USE_NAMES).addComponent(SomeSteps2.class);
        SomeSteps2 someSteps = parent.getComponent(SomeSteps2.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("the grid should look like $grid", THEN, SomeSteps2.class.getMethod(
				        "aMethodWith", String.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        Step step = candidateStep.createFrom(tableRow, "Then the grid should look like" + windowsNewline + ".." + unixNewline
                + ".." + windowsNewline);
        step.perform();
        ensureThat((String) someSteps.args, equalTo(".." + systemNewline + ".." + systemNewline));
    }

    @Test
    @Ignore
    public void shouldConvertArgsToListOfNumbers() throws Exception {
        parent.as(Characteristics.USE_NAMES).addComponent(SomeSteps2.class);
        SomeSteps2 someSteps = parent.getComponent(SomeSteps2.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("windows on the $nth floors",
        		WHEN, SomeSteps2.methodFor("aMethodWithListOfLongs"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "When windows on the 1L,2L,3L floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1L, 2L, 3L).toString()));

        candidateStep = new PicoEnabledCandidateStep("windows on the $nth floors", WHEN,
                SomeSteps2.methodFor("aMethodWithListOfIntegers"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "When windows on the 1,2,3 floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1, 2, 3).toString()));

        candidateStep = new PicoEnabledCandidateStep("windows on the $nth floors", WHEN,
                SomeSteps2.methodFor("aMethodWithListOfDoubles"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "When windows on the 1.1,2.2,3.3 floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1.1, 2.2, 3.3).toString()));

        candidateStep = new PicoEnabledCandidateStep("windows on the $nth floors", WHEN,
                SomeSteps2.methodFor("aMethodWithListOfFloats"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "When windows on the 1.1f,2.2f,3.3f floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1.1f, 2.2f, 3.3f).toString()));

    }

    @Test
    @Ignore
    public void shouldConvertArgsToListOfStrings() throws Exception {
        parent.as(Characteristics.USE_NAMES).addComponent(SomeSteps2.class);
        SomeSteps2 someSteps = parent.getComponent(SomeSteps2.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("windows on the $nth floors",
        		WHEN, SomeSteps2.methodFor("aMethodWithListOfStrings"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "When windows on the 1,2,3 floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList("1", "2", "3").toString()));
    }

    @Test
    public void shouldMatchMethodParametersByAnnotatedNamesInNaturalOrder() throws Exception {
        parent.as(Characteristics.USE_NAMES).addComponent(AnnotationNamedParameterSteps.class);
        AnnotationNamedParameterSteps steps = parent.getComponent(AnnotationNamedParameterSteps.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("I live on the $ith floor but some call it the $nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInNaturalOrder", AnnotationNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "When I live on the first floor but some call it the ground").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
    public void shouldMatchMethodParametersByAnnotatedNamesInverseOrder() throws Exception {
        parent.as(Characteristics.USE_NAMES).addComponent(AnnotationNamedParameterSteps.class);
        AnnotationNamedParameterSteps steps = parent.getComponent(AnnotationNamedParameterSteps.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("I live on the $ith floor but some call it the $nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInInverseOrder", AnnotationNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "When I live on the first floor but some call it the ground").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
    @Ignore
    public void shouldCreateStepFromTableValuesViaAnnotations() throws Exception {
    	AnnotationNamedParameterSteps steps = new AnnotationNamedParameterSteps();
    	tableRow.put("ith", "first");
    	tableRow.put("nth", "ground");
        CandidateStep candidateStep = new CandidateStep("I live on the ith floor but some call it the nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInNaturalOrder", AnnotationNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "When I live on the <ith> floor but some call it the <nth>").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
    public void shouldMatchMethodParametersByParanamerNamesInNaturalOrder() throws Exception {
        parent.as(Characteristics.USE_NAMES).addComponent(ParanamerNamedParameterSteps.class);
        ParanamerNamedParameterSteps steps = parent.getComponent(ParanamerNamedParameterSteps.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("I live on the $ith floor but some call it the $nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInNaturalOrder", ParanamerNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        Step step = candidateStep.createFrom(tableRow, "When I live on the first floor but some call it the ground");
        step.perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
    public void shouldMatchMethodParametersByParanamerInverseOrder() throws Exception {
        parent.as(Characteristics.USE_NAMES).addComponent(ParanamerNamedParameterSteps.class);
        ParanamerNamedParameterSteps steps = parent.getComponent(ParanamerNamedParameterSteps.class);
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("I live on the $ith floor but some call it the $nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInInverseOrder", ParanamerNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "When I live on the first floor but some call it the ground").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
    public void shouldCreateStepFromTableValuesViaParanamer() throws Exception {
        parent.as(Characteristics.USE_NAMES).addComponent(ParanamerNamedParameterSteps.class);
        ParanamerNamedParameterSteps steps = parent.getComponent(ParanamerNamedParameterSteps.class);
    	tableRow.put("ith", "first");
    	tableRow.put("nth", "ground");
        PicoEnabledCandidateStep candidateStep = new PicoEnabledCandidateStep("I live on the ith floor but some call it the nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInNaturalOrder", ParanamerNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords, parent);
        candidateStep.createFrom(tableRow, "When I live on the <ith> floor but some call it the <nth>").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
    public void shouldCreateStepsOfDifferentTypesWithSameMatchingPattern() throws Throwable {
        parent.addComponent(NamedTypeSteps.class);
        NamedTypeSteps steps = parent.getComponent(NamedTypeSteps.class);
        steps.withParentContainer(parent);
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(2));
        guard(candidateSteps[0].createFrom(tableRow, "Given foo named xyz").perform());
        guard(candidateSteps[1].createFrom(tableRow, "When foo named Bar").perform());
        ensureThat(steps.givenName, equalTo("xyz"));
        ensureThat(steps.whenName, equalTo("Bar"));
    }

    private StepResult guard(StepResult result) throws Throwable {
        if (result instanceof StepResult.Failed) {
            throw result.getThrowable();
        }
        return result;
    }

	@Test(expected= CandidateStep.StartingWordNotFound.class)
    public void shouldNotCreateStepOfWrongType() {
        parent.addComponent(NamedTypeSteps.class);
        NamedTypeSteps steps = parent.getComponent(NamedTypeSteps.class);
        steps.withParentContainer(parent);
        CandidateStep[] candidateSteps = steps.getSteps();
        ensureThat(candidateSteps.length, equalTo(2));
        candidateSteps[0].createFrom(tableRow, "Given foo named xyz").perform();
        ensureThat(steps.givenName, equalTo("xyz"));
        ensureThat(steps.whenName, nullValue());
        candidateSteps[0].createFrom(tableRow, "Then foo named xyz").perform();
    }

    public static class NamedTypeSteps extends PicoEnabledSteps {
        String givenName;
        String whenName;

        @Given("foo named $name")
        public void givenFoo(String name) {
        	givenName = name;
        }

        @When("foo named $name")
        public void whenFoo(String name) {
        	whenName = name;
        }

    }

    public static class AnnotationNamedParameterSteps extends PicoEnabledSteps {
    	String ith;
        String nth;

        public void methodWithNamedParametersInNaturalOrder(@Named("ith") String ithName, @Named("nth") String nthName){
    		this.ith = ithName;
    		this.nth = nthName;
        }

        public void methodWithNamedParametersInInverseOrder(@Named("nth") String nthName, @Named("ith") String ithName){
    		this.ith = ithName;
    		this.nth = nthName;
        }

    }

    public static class ParanamerNamedParameterSteps extends PicoEnabledSteps {
    	String ith;
        String nth;

        public void methodWithNamedParametersInNaturalOrder(String ith, String nth){
    		this.ith = ith;
    		this.nth = nth;
        }

        public void methodWithNamedParametersInInverseOrder(String nth, String ith){
    		this.ith = ith;
    		this.nth = nth;
        }

    }

    static Method stepMethodFor(String methodName, Class<? extends Steps> stepsClass) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(stepsClass);
        for (MethodDescriptor md : beanInfo.getMethodDescriptors()) {
            if (md.getMethod().getName().equals(methodName)) {
                return md.getMethod();
            }
        }
        return null;
    }

}
