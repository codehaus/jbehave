package org.jbehave.plugin.idea;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.InvalidDataException;
import org.jdom.Element;

import java.util.HashMap;
import java.util.Map;

abstract public class RunConfigurationField {
  private static Map<String, RunConfigurationField> fields = new HashMap<String, RunConfigurationField>();
  private String name;

  public RunConfigurationField(String name) {
    this.name = name;
    fields.put(name, this);
  }

  public String getName() {
    return name;
  }

  abstract public String getValue(JBehaveRunConfiguration runConfiguration);

  abstract public void setValue(JBehaveRunConfiguration runConfiguration, String value);

  public static RunConfigurationField getField(String fieldName) {
    RunConfigurationField field = fields.get(fieldName);
    return field == null ? NULL : field;
  }

  public static final RunConfigurationField NULL = new RunConfigurationField("") {
    public String getValue(JBehaveRunConfiguration runConfiguration) {
      return "";
    }

    public void setValue(JBehaveRunConfiguration runConfiguration, String value) {
    }
  };

  public static final RunConfigurationField CLASS_NAME = new RunConfigurationField("behaviorClass") {
    public String getValue(JBehaveRunConfiguration runConfiguration) {
      return runConfiguration.getBehaviorClass();
    }

    public void setValue(JBehaveRunConfiguration runConfiguration, String value) {
      runConfiguration.setBehaviorClass(value);
    }
  };

  public static final RunConfigurationField METHOD_NAME = new RunConfigurationField("behaviorMethod") {
    public String getValue(JBehaveRunConfiguration runConfiguration) {
      return runConfiguration.getBehaviourMethod();
    }

    public void setValue(JBehaveRunConfiguration runConfiguration, String value) {
      runConfiguration.setBehaviorMethod(value);
    }
  };

  public static final RunConfigurationField MODULE = new RunConfigurationField("moduleName") {
    public String getValue(JBehaveRunConfiguration runConfiguration) {
      Module module = runConfiguration.getModule();
      return module == null ? "" : module.getName();
    }

    public void setValue(JBehaveRunConfiguration runConfiguration, String value) {
      runConfiguration.setModuleByName(value);
    }
  };

  public static void writeToElement(JBehaveRunConfiguration behaveRunConfiguration, Element parentNode) {
    for (RunConfigurationField field : fields.values()) {
      String value = field.getValue(behaveRunConfiguration);
      if (value.length() != 0) {
        Element element = new Element("option");
        parentNode.addContent(element);
        element.setAttribute("name", field.getName());
        element.setAttribute("value", value);
      }
    }
  }

  static void readFromElement(JBehaveRunConfiguration runConfiguration, Element element) throws InvalidDataException {
    for (final Object o : element.getChildren("option")) {
      Element e = (Element) o;
      String fieldName = e.getAttributeValue("name");
      if (fieldName == null) {
        throw new InvalidDataException("element should have name attribute:" + element);
      }
      getField(fieldName).setValue(runConfiguration, e.getAttributeValue("value"));
    }
  }
}
