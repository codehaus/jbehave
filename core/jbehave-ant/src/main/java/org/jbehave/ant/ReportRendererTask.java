package org.jbehave.ant;

import static org.apache.tools.ant.Project.*;

import java.io.File;

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
     * The format of the rendering
     */
    private String format = "html";

    public void execute() throws BuildException {
        ReportRenderer renderer = new FreemarkerReportRenderer();
        try {
            log("Rendering reports in '" + outputDirectory + "' using format '" + format + "'", MSG_INFO);
            renderer.render(new File(outputDirectory), format);
        } catch (Throwable e) {
            String message = "Failed to render reports in '" + outputDirectory + "' using format '" + format + "'";
            log(message, MSG_WARN);
            throw new BuildException(message, e);
        }
    }
    
    // Setters

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    
}
