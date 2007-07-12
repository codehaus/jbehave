package org.jbehave.core.story.codegen.velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.jbehave.core.story.codegen.CodeGenerator;
import org.jbehave.core.story.codegen.domain.ScenarioDetails;
import org.jbehave.core.story.codegen.domain.StoryDetails;
import org.jbehave.core.util.CamelCaseConverter;

/**
 * Velocity-based code generator.  Generates java source for the events, givens and outcomes of a story.
 * 
 * @author Mauro Talevi
 */
public class VelocityCodeGenerator implements CodeGenerator {

    private static final String SOURCE_PATH = "{0}/{1}s/{2}.java";
    private static final String PACKAGE_NAME = "{0}.{1}s";
    private static final String TEMPLATE_PATH = "org/jbehave/core/story/codegen/velocity/templates/{0}.template";

    private String rootSourceDirectory;
    private String rootPackageName;

    /** The velocity engine */
    private VelocityEngine engine;

    /**
     * Creates a VelocityCodeGenerator
     * 
     * @param rootSourceDirectory the root source directory of the generated
     *            story code
     * @param rootPackageName the root package name of the story
     */
    public VelocityCodeGenerator(String rootSourceDirectory, String rootPackageName) {
        this.rootSourceDirectory = rootSourceDirectory;
        this.rootPackageName = rootPackageName;
        this.engine = new VelocityEngine();
        initialiseEngine(engine);
    }

    public void generateStory(StoryDetails storyDetails) {
        for (Iterator i = storyDetails.scenarios.iterator(); i.hasNext();) {
            ScenarioDetails scenario = (ScenarioDetails) i.next();
            generateSource(scenario.event.name, "event");
            for (Iterator j = scenario.context.givens.iterator(); j.hasNext();) {
                generateSource((String) j.next(), "given");
            }
            for (Iterator j = scenario.outcome.outcomes.iterator(); j.hasNext();) {
                generateSource((String) j.next(), "outcome");
            }
        }
    }

    private String toCamelCase(String name) {
        return new CamelCaseConverter(name).toCamelCase();
    }

    private void generateSource(String name, String type) {
        String className = toCamelCase(name);
        String sourcePath = MessageFormat.format(SOURCE_PATH, new Object[] { rootSourceDirectory, type, className });
        String packageName = MessageFormat.format(PACKAGE_NAME, new Object[] { rootPackageName, type });
        String templatePath = MessageFormat.format(TEMPLATE_PATH, new Object[] { type });
        generateSource(sourcePath, templatePath, className, packageName);
    }

    private void generateSource(String sourcePath, String templatePath, String className, String packageName) {
        try {
            VelocityContext context = new VelocityContext();
            context.put("className", className);
            context.put("packageName", packageName);
            File file = new File(sourcePath);
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(file);
            processTemplate(templatePath, context, writer);
            writer.close();
        } catch (IOException e) {
            throw new CodeGenerationFailedException("Failed to generate source", e);
        }
    }

    /**
     * Initialises VelocityEngine to retrieve resources from classpath
     * 
     * @param engine the VelocityEngine
     */
    private void initialiseEngine(VelocityEngine engine) {
        try {
            Properties properties = new Properties();
            properties.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
            properties.setProperty("classpath." + VelocityEngine.RESOURCE_LOADER + ".class",
                    ClasspathResourceLoader.class.getName());
            engine.init(properties);
        } catch (Exception e) {
            throw new CodeGenerationFailedException("Failed to initialise VelocityEngine " + engine, e);
        }
    }

    /**
     * Processes a velocity template and writes output to writer
     * 
     * @param templatePath the template resource path
     * @param context the VelocityContext
     * @param writer the Writer to write output to
     */
    private void processTemplate(String templatePath, VelocityContext context, Writer writer) {
        try {
            Template template = engine.getTemplate(templatePath);
            template.merge(context, writer);
        } catch (Exception e) {
            throw new CodeGenerationFailedException("Failed to process template " + templatePath + " with context " + context, e);
        }
    }

    public static class CodeGenerationFailedException extends RuntimeException {
        public CodeGenerationFailedException(String message, Throwable cause) {
            super(message, cause);
        }        
    }
}
