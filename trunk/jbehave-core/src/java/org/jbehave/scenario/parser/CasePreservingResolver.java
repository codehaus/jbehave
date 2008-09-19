package org.jbehave.scenario.parser;

import org.jbehave.scenario.JUnitScenario;

/**
 * <p>
 * Resolves scenario names while preserving the Java scenario class case eg:
 * "org.jbehave.scenario.ICanLogin.java" -> "org/jbehave/scenario/ICanLogin".
 * </p>
 * <p>
 * By default no extension is used, but this can be configured via the
 * constructor so that we can resolve name to eg
 * "org/jbehave/scenario/ICanLogin.scenario".
 * </p>
 */
public class CasePreservingResolver implements ScenarioNameResolver {

    private static final String DOT_REGEX = "\\.";
    private static final String SLASH = "/";
    private final String extension;

    public CasePreservingResolver(String extension) {
        this.extension = extension;
    }

    public String resolve(Class<? extends JUnitScenario> scenarioClass) {
        String packageDir = scenarioClass.getPackage().getName().replaceAll(DOT_REGEX, SLASH);
        return packageDir + SLASH + scenarioClass.getSimpleName() + extension;
    }

}
