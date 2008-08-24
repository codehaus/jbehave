package org.jbehave.scenario.parser;

import org.jbehave.scenario.Scenario;

public class CasePreservingResolver implements ScenarioFileNameResolver {

    private static final String DOT_REGEX = "\\.";
    private static final String SLASH = "/";
    private final String extension;

    public CasePreservingResolver(String extension) {
        this.extension = extension;
    }

    public String resolve(Class<? extends Scenario> scenarioClass) {
        String packageDir = scenarioClass.getPackage().getName().replaceAll(DOT_REGEX, SLASH);        
        return packageDir + SLASH + scenarioClass.getSimpleName() + extension;
    }

}
