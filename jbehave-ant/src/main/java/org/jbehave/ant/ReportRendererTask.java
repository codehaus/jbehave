package org.jbehave.ant;

import static java.util.Arrays.asList;
import static org.apache.tools.ant.Project.MSG_INFO;
import static org.apache.tools.ant.Project.MSG_WARN;

import java.io.*;
import java.util.List;
import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.jbehave.scenario.reporters.FreemarkerReportRenderer;
import org.jbehave.scenario.reporters.ReportRenderer;

/**
 * Ant task that renders reports
 * 
 * @author Mauro Talevi
 */
public class ReportRendererTask extends Task {

    /**
     * The output directory of the reports
     */
    private String outputDirectory = "target/jbehave-reports";

    /**
     * The format of the generated output
     */
    private List<String> formats = asList();

    /**
     * The flag to skip
     */
    private boolean skip = false;
    
    /**
     * The flag to ignore failures
     */
    private boolean ignoreFailure = false;
    
    /**
     * The template properties
     */
    private Properties templateProperties = new Properties();

    public void execute() throws BuildException {
        if (skip) {
            log("Skipped rendering reports", MSG_INFO);
            return;
        }
        ReportRenderer renderer = new FreemarkerReportRenderer(templateProperties);
        try {
            log("Rendering reports in '" + outputDirectory + "' using formats '" + formats + "'"
               +" and template properties '"+templateProperties+"'", MSG_INFO);
            renderer.render(new File(outputDirectory), formats);
        } catch (Throwable e) {
            String message = "Failed to render reports in '" + outputDirectory + "' using formats '" + formats + "'"
                            +" and template properties '"+templateProperties+"'";
            log(message, MSG_WARN);
            throw new BuildException(message, e);
        }
        int scenarios = renderer.countScenarios();
        int failed = renderer.countFailedScenarios();
    	String message = "Rendered reports with "+scenarios+" scenarios (of which "+failed+" failed)";
    	log(message, MSG_INFO);
        if ( !ignoreFailure && failed > 0 ){
			throw new BuildException(message);
        }
    }
    
    // Setters

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void setFormats(String formats) {
        this.formats = asList(formats.split(","));
    }
    
    public void setTemplateProperties(String properties){
        try {
            templateProperties.load(new ByteArrayInputStream(properties.getBytes()));
        } catch (IOException e) {
            String message = "Failed to load template properties: "+properties;
            log(message, MSG_WARN);
        }        
    }
    
    public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public void setIgnoreFailure(boolean ignoreFailure){
    	this.ignoreFailure = ignoreFailure;    	
    }
}
