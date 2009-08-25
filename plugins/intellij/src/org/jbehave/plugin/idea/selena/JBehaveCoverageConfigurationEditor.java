package org.jbehave.plugin.idea.selena;

import com.intellij.execution.configurations.coverage.CoverageConfigurable;
import com.intellij.execution.util.JreVersionDetector;
import com.intellij.openapi.project.Project;

public class JBehaveCoverageConfigurationEditor extends CoverageConfigurable<JBehaveConfiguration>
{
  private JreVersionDetector myVersionDetector = new JreVersionDetector();

  public JBehaveCoverageConfigurationEditor(Project project) {
    super(project);
  }

  protected boolean isJre50Configured(JBehaveConfiguration configuration) {
    return myVersionDetector
        .isJre50Configured(configuration, configuration.ALTERNATIVE_JRE_PATH_ENABLED, configuration.ALTERNATIVE_JRE_PATH);
  }
}
