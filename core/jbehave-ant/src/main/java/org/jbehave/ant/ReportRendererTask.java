package org.jbehave.ant;

import static java.util.Arrays.asList;
import static org.apache.tools.ant.Project.MSG_INFO;
import static org.apache.tools.ant.Project.MSG_WARN;

import java.io.File;
import java.util.List;

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
    private List<String> formats = asList("html", "xml", "txt");

    public void execute() throws BuildException {
        ReportRenderer renderer = new FreemarkerReportRenderer();
        try {
            log("Rendering reports in '" + outputDirectory + "' using formats '" + formats + "'", MSG_INFO);
            renderer.render(new File(outputDirectory), formats);
        } catch (Throwable e) {
            String message = "Failed to render reports in '" + outputDirectory + "' using formats '" + formats + "'";
            log(message, MSG_WARN);
            throw new BuildException(message, e);
        }
    }
    
    // Setters

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void setFormats(String formats) {
        this.formats = asList(formats.split(","));
    }

    
}
