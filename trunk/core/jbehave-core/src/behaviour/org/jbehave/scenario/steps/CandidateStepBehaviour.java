package org.jbehave.scenario.steps;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.jbehave.Ensure.ensureThat;
import static org.jbehave.Ensure.not;
import static org.jbehave.scenario.steps.StepType.GIVEN;
import static org.jbehave.scenario.steps.StepType.THEN;
import static org.jbehave.scenario.steps.StepType.WHEN;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbehave.scenario.annotations.Named;
import org.jbehave.scenario.parser.PrefixCapturingPatternBuilder;
import org.jbehave.scenario.parser.StepPatternBuilder;
import org.jbehave.scenario.reporters.ScenarioReporter;
import org.junit.Test;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class CandidateStepBehaviour {

    private static final StepPatternBuilder PATTERN_BUILDER = new PrefixCapturingPatternBuilder();
    private static final String NL = System.getProperty("line.separator");
	private Map<String, String> tableRow = new HashMap<String, String>();
	private Paranamer paranamer =  new CachingParanamer(new BytecodeReadingParanamer());
	private Map<StepType, String> startingWords = startingWords();

	private Map<StepType, String> startingWords() {
		Map<StepType, String> map = new HashMap<StepType, String>();
		map.put(GIVEN, "Given");
		map.put(WHEN, "When");
		map.put(THEN, "Then");
		return map;
	}
           
    @Test
    public void shouldMatchASimpleString() throws Exception {
        CandidateStep candidateStep = new CandidateStep("I laugh", GIVEN, SomeSteps.class.getMethod("aMethod"),
                null, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        ensureThat(candidateStep.matches("Given I laugh"));
    }

    @Test
    public void shouldMatchAStringWithArguments() throws Exception {
        CandidateStep candidateStep = new CandidateStep("windows on the $nth floor", WHEN, SomeSteps.class
				        .getMethod("aMethod"), null, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        ensureThat(candidateStep.matches("When windows on the 1st floor"));
        ensureThat(not(candidateStep.matches("When windows on the 1st floor are open")));
    }

    @Test
    public void shouldProvideARealStepUsingTheMatchedString() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("I live on the $nth floor", THEN, SomeSteps.class.getMethod(
				        "aMethodWith", String.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        Step step = candidateStep.createFrom(tableRow, "Then I live on the 1st floor");
        step.perform();
        ensureThat((String) someSteps.args, equalTo("1st"));
    }

    @Test
    public void shouldMatchMultilineStrings() throws Exception {
        CandidateStep candidateStep = new CandidateStep("the grid should look like $grid", THEN, SomeSteps.class
				        .getMethod("aMethod"), null, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        ensureThat(candidateStep.matches("Then the grid should look like " + NL + "...." + NL + "...." + NL));
    }

    @Test
    public void shouldConvertArgsToAppropriateNumbers() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("I should live in no. $no", THEN, SomeSteps.class.getMethod(
				        "aMethodWith", int.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "Then I should live in no. 14").perform();
        ensureThat((Integer) someSteps.args, equalTo(14));

        candidateStep = new CandidateStep("I should live in no. $no", THEN, SomeSteps.class.getMethod("aMethodWith",
				        long.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "Then I should live in no. 14").perform();
        ensureThat((Long) someSteps.args, equalTo(14L));

        candidateStep = new CandidateStep("I should live in no. $no", THEN, SomeSteps.class.getMethod("aMethodWith",
				        double.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "Then I should live in no. 14").perform();
        ensureThat((Double) someSteps.args, equalTo(14.0));

        candidateStep = new CandidateStep("I should live in no. $no", THEN, SomeSteps.class.getMethod("aMethodWith",
				        float.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "Then I should live in no. 14").perform();
        ensureThat((Float) someSteps.args, equalTo(14.0f));
    }

    @Test
    public void shouldProvideAStepWithADescriptionThatMatchesTheCandidateStep() throws Exception {
        ScenarioReporter reporter = mock(ScenarioReporter.class);
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("I live on the $nth floor", THEN, SomeSteps.class.getMethod(
				        "aMethodWith", String.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        Step step = candidateStep.createFrom(tableRow, "Then I live on the 1st floor");

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
        CandidateStep candidateStep = new CandidateStep("the grid should look like $grid", THEN, SomeSteps.class.getMethod(
				        "aMethodWith", String.class), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        Step step = candidateStep.createFrom(tableRow, "Then the grid should look like" + windowsNewline + ".." + unixNewline
                + ".." + windowsNewline);
        step.perform();
        ensureThat((String) someSteps.args, equalTo(".." + systemNewline + ".." + systemNewline));
    }

    @Test
    public void shouldConvertArgsToListOfNumbers() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("windows on the $nth floors",
        		WHEN, SomeSteps.methodFor("aMethodWithListOfLongs"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "When windows on the 1L,2L,3L floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1L, 2L, 3L).toString()));

        candidateStep = new CandidateStep("windows on the $nth floors", WHEN,
                SomeSteps.methodFor("aMethodWithListOfIntegers"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "When windows on the 1,2,3 floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1, 2, 3).toString()));

        candidateStep = new CandidateStep("windows on the $nth floors", WHEN,
                SomeSteps.methodFor("aMethodWithListOfDoubles"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "When windows on the 1.1,2.2,3.3 floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1.1, 2.2, 3.3).toString()));

        candidateStep = new CandidateStep("windows on the $nth floors", WHEN,
                SomeSteps.methodFor("aMethodWithListOfFloats"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "When windows on the 1.1f,2.2f,3.3f floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList(1.1f, 2.2f, 3.3f).toString()));

    }

    @Test
    public void shouldConvertArgsToListOfStrings() throws Exception {
        SomeSteps someSteps = new SomeSteps();
        CandidateStep candidateStep = new CandidateStep("windows on the $nth floors",
        		WHEN, SomeSteps.methodFor("aMethodWithListOfStrings"), someSteps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "When windows on the 1,2,3 floors").perform();
        ensureThat(((List<?>) someSteps.args).toString(), equalTo(asList("1", "2", "3").toString()));
    }
    
    @Test
    public void shouldMatchMethodParametersByAnnotatedNamesInNaturalOrder() throws Exception {
    	AnnotationNamedParameterSteps steps = new AnnotationNamedParameterSteps();
        CandidateStep candidateStep = new CandidateStep("I live on the $ith floor but some call it the $nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInNaturalOrder", AnnotationNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "When I live on the first floor but some call it the ground").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
    public void shouldMatchMethodParametersByAnnotatedNamesInverseOrder() throws Exception {
    	AnnotationNamedParameterSteps steps = new AnnotationNamedParameterSteps();
        CandidateStep candidateStep = new CandidateStep("I live on the $ith floor but some call it the $nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInInverseOrder", AnnotationNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.createFrom(tableRow, "When I live on the first floor but some call it the ground").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
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
    	ParanamerNamedParameterSteps steps = new ParanamerNamedParameterSteps();
        CandidateStep candidateStep = new CandidateStep("I live on the $ith floor but some call it the $nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInNaturalOrder", ParanamerNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.useParanamer(paranamer);
        candidateStep.createFrom(tableRow, "When I live on the first floor but some call it the ground").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
    public void shouldMatchMethodParametersByParanamerInverseOrder() throws Exception {
    	ParanamerNamedParameterSteps steps = new ParanamerNamedParameterSteps();
        CandidateStep candidateStep = new CandidateStep("I live on the $ith floor but some call it the $nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInInverseOrder", ParanamerNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.useParanamer(paranamer);
        candidateStep.createFrom(tableRow, "When I live on the first floor but some call it the ground").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    @Test
    public void shouldCreateStepFromTableValuesViaParanamer() throws Exception {
    	ParanamerNamedParameterSteps steps = new ParanamerNamedParameterSteps();
    	tableRow.put("ith", "first");
    	tableRow.put("nth", "ground");
        CandidateStep candidateStep = new CandidateStep("I live on the ith floor but some call it the nth",
        		WHEN, stepMethodFor("methodWithNamedParametersInNaturalOrder", ParanamerNamedParameterSteps.class), steps, PATTERN_BUILDER, new ParameterConverters(), startingWords);
        candidateStep.useParanamer(paranamer);
        candidateStep.createFrom(tableRow, "When I live on the <ith> floor but some call it the <nth>").perform();
        ensureThat(steps.ith, equalTo("first"));
        ensureThat(steps.nth, equalTo("ground"));
    }

    static class AnnotationNamedParameterSteps extends Steps {
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

    static class ParanamerNamedParameterSteps extends Steps {
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
