package org.jbehave.scenario.steps.pico;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;

import org.jbehave.scenario.errors.PendingError;
import org.jbehave.scenario.parser.StepPatternBuilder;
import org.jbehave.scenario.steps.CandidateStep;
import org.jbehave.scenario.steps.CandidateSteps;
import org.jbehave.scenario.steps.ParameterConverters;
import org.jbehave.scenario.steps.Step;
import org.jbehave.scenario.steps.StepMonitor;
import org.jbehave.scenario.steps.StepResult;
import org.jbehave.scenario.steps.StepType;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;
import org.picocontainer.injectors.MethodInjection;
import org.picocontainer.injectors.Reinjection;
import org.picocontainer.lifecycle.NullLifecycleStrategy;

public class PicoEnabledCandidateStep extends CandidateStep {

    private PicoContainer parent;

    public PicoEnabledCandidateStep(String patternAsString, StepType stepType, Method method, CandidateSteps steps,
                                    StepPatternBuilder patternBuilder, ParameterConverters parameterConverters,
                                    Map<StepType, String> startingWords, PicoContainer parent) {
        super(patternAsString, stepType, method, steps, patternBuilder, parameterConverters, startingWords);
        this.parent = parent;
    }

    @Override
    protected Step createStep(final String stepAsString, Map<String, String> tableRow, Matcher matcher, final Method method,
                              final StepMonitor stepMonitor, String[] groupNames) {

        final MutablePicoContainer stepContainer = new DefaultPicoContainer(new Reinjection(new MethodInjection(method), parent), new NullLifecycleStrategy(), parent);

        // populate named entries from table data
        for (String key : tableRow.keySet()) {
            stepContainer.addConfig(key, tableRow.get(key));
        }

        // populate named entries from regex match
        for (String groupName : groupNames) {
            stepContainer.addConfig(groupName, getGroup(matcher, groupName));
        }

        return new Step() {
            public StepResult perform() {
                try {
                    stepMonitor.performing(stepAsString);
                    stepContainer.as(Characteristics.USE_NAMES).addComponent(method.getDeclaringClass());
                    stepContainer.getComponent(method.getDeclaringClass());
                    return StepResult.success(stepAsString);
                } catch (Throwable t) {
                    return failureWithOriginalException(stepAsString, t);
                }
            }

            private StepResult failureWithOriginalException(final String stepAsString, Throwable t) {
                if (t instanceof InvocationTargetException && t.getCause() != null) {
                    if (t.getCause() instanceof PendingError) {
                        return StepResult.pending(stepAsString, (PendingError) t.getCause());
                    } else {
                        return StepResult.failure(stepAsString, t.getCause());
                    }
                }
                return StepResult.failure(stepAsString, t);
            }

            public StepResult doNotPerform() {
                return StepResult.notPerformed(stepAsString);
            }

        };
    }
}
