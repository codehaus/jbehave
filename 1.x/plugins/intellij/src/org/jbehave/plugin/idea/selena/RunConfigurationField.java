package org.jbehave.plugin.idea.selena;

import java.util.HashMap;
import java.util.Map;

import com.intellij.openapi.util.InvalidDataException;
import org.jdom.Element;

abstract public class RunConfigurationField
{
  private static Map<String, RunConfigurationField> fields = new HashMap<String, RunConfigurationField>();
  private String name;

  public RunConfigurationField(String name) {
    this.name = name;
    fields.put(name, this);
  }

  public String getName() {
    return name;
  }

  abstract public String getValue(ConfigurationData configuration);

  abstract public void setValue(ConfigurationData configuration, String value);

  public static RunConfigurationField getField(String fieldName) {
    RunConfigurationField field = fields.get(fieldName);
    return field == null ? NULL : field;
  }

  public static final RunConfigurationField NULL = new RunConfigurationField("")
  {
    public String getValue(ConfigurationData configuration) {
      return "";
    }

    public void setValue(ConfigurationData configuration, String value) {
    }
  };

  public static final RunConfigurationField CLASS_NAME = new RunConfigurationField("behaviorClass")
  {
    public String getValue(ConfigurationData configuration) {
      return configuration.getBehaviorClass();
    }

    public void setValue(ConfigurationData configuration, String value) {
      configuration.setBehaviorClass(value);
    }
  };

  public static final RunConfigurationField METHOD_NAME = new RunConfigurationField("behaviorMethod")
  {
    public String getValue(ConfigurationData configuration) {
      return configuration.getBehaviourMethod();
    }

    public void setValue(ConfigurationData configuration, String value) {
      configuration.setBehaviorMethod(value);
    }
  };

  public static final RunConfigurationField MODULE = new RunConfigurationField("moduleName")
  {
    public String getValue(ConfigurationData configuration) {
      return configuration.getModuleName();
    }

    public void setValue(ConfigurationData configuration, String value) {
      configuration.setModuleName(value);
    }
  };

  public static void writeToElement(ConfigurationData data, Element parentNode) {
    for (RunConfigurationField field : fields.values()) {
      String value = field.getValue(data);
      if (value.length() != 0) {
        Element element = new Element("option");
        parentNode.addContent(element);
        element.setAttribute("name", field.getName());
        element.setAttribute("value", value);
      }
    }
  }

  static void readFromElement(ConfigurationData data, Element element) throws InvalidDataException {
    for (final Object o : element.getChildren("option")) {
      Element e = (Element) o;
      String fieldName = e.getAttributeValue("name");
      if (fieldName == null) {
        throw new InvalidDataException("element should have name attribute:" + element);
      }
      getField(fieldName).setValue(data, e.getAttributeValue("value"));
    }
  }
}