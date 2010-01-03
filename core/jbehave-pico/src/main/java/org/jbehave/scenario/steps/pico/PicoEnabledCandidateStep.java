package org.jbehave.scenario.steps.pico;

import org.jbehave.scenario.errors.PendingError;
import org.jbehave.scenario.parser.StepPatternBuilder;
import org.jbehave.scenario.steps.*;
import org.picocontainer.Characteristics;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;
import org.picocontainer.injectors.MethodInjection;
import org.picocontainer.injectors.Reinjection;
import org.picocontainer.injectors.Reinjector;
import org.picocontainer.lifecycle.NullLifecycleStrategy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;

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

        final MutablePicoContainer tempContainer = new DefaultPicoContainer(new Reinjection(new MethodInjection(method), parent), new NullLifecycleStrategy(), parent);

        // populate named entries from tableRow
        Set<String> keys = tableRow.keySet();
        for (String key : keys) {
            tempContainer.addConfig(key, tableRow.get(key));
        }

        // populate named entries from regex
        for (String groupName : groupNames) {
            String val = getGroup(matcher, groupName);
            tempContainer.addConfig(groupName, val);
        }

        return new Step() {
            public StepResult perform() {
                try {
                    stepMonitor.performing(stepAsString);
                    tempContainer.as(Characteristics.USE_NAMES).addComponent(method.getDeclaringClass());
                    tempContainer.getComponent(method.getDeclaringClass());
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
