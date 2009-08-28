package org.jbehave.scenario.steps;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;
import static org.jbehave.Ensure.not;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import org.jbehave.scenario.annotations.Named;
import org.jbehave.scenario.parser.PrefixCapturingPatternBuilder;
import org.jbehave.scenario.parser.StepPatternBuilder;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.junit.Test;

public class CandidateStepBehaviour {

    private static final StepPatternBuilder PATTERN_BUILDER = new PrefixCapturingPatternBuilder();
    private static final StepMonitor MONITOR = new SilentStepMonitor();
    private static final String NL = System.getProperty("line.separator");

    @Test
    public void shouldMatchASimpleString() throws Exception {
        CandidateStep candidateStep = new CandidateStep("I laugh", SomeSteps.class.getMethod("aMethod"), null,
                PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        ensureThat(candidateStep.matches("Given I laugh"));
    }

    @Test
    public void shouldMatchAStringWithArguments() throws Exception {
        CandidateStep candidateStep = new CandidateStep("windows on the $nth floor", SomeSteps.class
                .getMethod("aMethod"), null, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        ensureThat(candidateStep.matches("When windows on the 1st floor"));
        ensureThat(not(candidateStep.matches("When windows on the 1st floor are open")));
    }

    @Test
    public void shouldProvideARealStepUsingTheMatchedString() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("I live on the $nth floor", SomeSteps.class.getMethod(
                "aMethodWith", String.class), someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        Step step = candidateStep.createFrom("Then I live on the 1st floor");
        step.perform();
        ensureThat((String) someSteps.args, equalTo("1st"));
    }

    @Test
    public void shouldMatchMultilineStrings() throws Exception {
        CandidateStep candidateStep = new CandidateStep("the grid should look like $grid", SomeSteps.class
                .getMethod("aMethod"), null, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        ensureThat(candidateStep.matches("Then the grid should look like " + NL + "...." + NL + "...." + NL));
    }

    @Test
    public void shouldConvertArgsToAppropriateNumbers() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("I should live in no. $no", SomeSteps.class.getMethod(
                "aMethodWith", int.class), someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("Then I should live in no. 14").perform();
        ensureThat((Integer) someSteps.args, equalTo(14));

        candidateStep = new CandidateStep("I should live in no. $no", SomeSteps.class.getMethod("aMethodWith",
                long.class), someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("Then I should live in no. 14").perform();
        ensureThat((Long) someSteps.args, equalTo(14L));

        candidateStep = new CandidateStep("I should live in no. $no", SomeSteps.class.getMethod("aMethodWith",
                double.class), someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("Then I should live in no. 14").perform();
        ensureThat((Double) someSteps.args, equalTo(14.0));

        candidateStep = new CandidateStep("I should live in no. $no", SomeSteps.class.getMethod("aMethodWith",
                float.class), someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("Then I should live in no. 14").perform();
        ensureThat((Float) someSteps.args, equalTo(14.0f));
    }

    @Test
    public void shouldProvideAStepWithADescriptionThatMatchesTheCandidateStep() throws Exception {
        ScenarioReporter reporter = mock(ScenarioReporter.class);
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("I live on the $nth floor", SomeSteps.class.getMethod(
                "aMethodWith", String.class), someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        Step step = candidateStep.createFrom("Then I live on the 1st floor");

        StepResult result = step.perform();
        result.describeTo(reporter);
        verify(reporter).successful("Then I live on the 1st floor");
    }

    @Test
    public void shouldNotFailJustBecauseWeHaveDifferentNewlinesToTheOneTheScenarioWasWrittenIn() throws Exception {
        String windowsNewline = "\r\n";
        String unixNewline = "\n";
        String systemNewline = System.getProperty("line.separator");
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("the grid should look like $grid", SomeSteps.class.getMethod(
                "aMethodWith", String.class), someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        Step step = candidateStep.createFrom("Then the grid should look like" + windowsNewline + ".." + unixNewline
                + ".." + windowsNewline);
        step.perform();
        ensureThat((String) someSteps.args, equalTo(".." + systemNewline + ".." + systemNewline));
    }

    @Test
    public void shouldConvertArgsToListOfNumbers() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("windows on the $nth floors",
                SomeSteps.methodFor("aMethodWithListOfLongs"), someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("When windows on the 1L,2L,3L floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1L, 2L, 3L).toString()));

        candidateStep = new CandidateStep("windows on the $nth floors", SomeSteps.methodFor("aMethodWithListOfIntegers"),
                someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("When windows on the 1,2,3 floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1, 2, 3).toString()));

        candidateStep = new CandidateStep("windows on the $nth floors", SomeSteps.methodFor("aMethodWithListOfDoubles"),
                someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("When windows on the 1.1,2.2,3.3 floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1.1, 2.2, 3.3).toString()));

        candidateStep = new CandidateStep("windows on the $nth floors", SomeSteps.methodFor("aMethodWithListOfFloats"),
                someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("When windows on the 1.1f,2.2f,3.3f floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1.1f, 2.2f, 3.3f).toString()));

    }

    @Test
    public void shouldConvertArgsToListOfStrings() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("windows on the $nth floors",
                SomeSteps.methodFor("aMethodWithListOfStrings"), someSteps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("When windows on the 1,2,3 floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList("1", "2", "3").toString()));
    }
    
    @Test
    public void shouldMatchMethodParametersByAnnotatedNames() throws Exception {
    	NamedParameterSteps steps = new NamedParameterSteps();
        CandidateStep candidateStep = new CandidateStep("I live on the $ith floor but some call it the $nth",
        		NamedParameterSteps.methodFor("methodWithNamedParametersInOrder"), steps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("When I live on the first floor but some call it the ground").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
        candidateStep = new CandidateStep("I live on the $ith floor but some call it the $nth",
        		NamedParameterSteps.methodFor("methodWithNamedParametersInInverseOrder"), steps, PATTERN_BUILDER, MONITOR, new ParameterConverters(), "Given", "When", "Then");
        candidateStep.createFrom("When I live on the first floor but some call it the ground").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }
    
    public static class NamedParameterSteps extends Steps {
    	String ith;
        String nth;

        public void methodWithNamedParametersInOrder(@Named("ith") String ithName, @Named("nth") String nthName){
    		this.ith = ithName;    	
    		this.nth = nthName;
        }
        
        public void methodWithNamedParametersInInverseOrder(@Named("nth") String nthName, @Named("ith") String ithName){
    		this.ith = ithName;    	
    		this.nth = nthName;
        }

        public static Method methodFor(String methodName) throws IntrospectionException {
            BeanInfo beanInfo = Introspector.getBeanInfo(NamedParameterSteps.class);
            for (MethodDescriptor md : beanInfo.getMethodDescriptors()) {
                if (md.getMethod().getName().equals(methodName)) {
                    return md.getMethod();
                }
            }
            return null;
        }
    }
    
}
