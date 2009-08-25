package org.jbehave.ant;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;

public class AbstractStoryTask extends AbstractJavaTask {

    private final Class commandLineClass;
    private final FilesetParser filesetParser;
    private List targetArgumentList = new LinkedList();
    private List filesets = new ArrayList();

    public AbstractStoryTask(Class commandLineClass, CommandRunner runner, FilesetParser filesetParser) {
        super(runner);
        this.commandLineClass = commandLineClass;
        this.filesetParser = filesetParser;
    }

    public void addTarget(String targetArgument) {
        targetArgumentList.add(targetArgument);
    }

    public void addTarget(int index, String dir) {
        targetArgumentList.add(index, dir);
    }

    public void execute() {
        appendAntTaskJar();
        addArgumentsAndRun();
    }

    public void setCloneVm(boolean cloneVm) {
        commandLine.setCloneVm(cloneVm);
    }

    private void appendAntTaskJar() {
        createClasspath().append(new Path(getProject(), locate()));
    }

    private void addArgumentsAndRun() {

        for (Iterator iter = filesets.iterator(); iter.hasNext();) {
            FileSet fileset = (FileSet) iter.next();

            String[] arguments = getArguments(fileset, filesetParser);
            
            for (int i = 0; i < arguments.length; i++) {
                addTarget(arguments[i]);
            }
        }
        
        commandLine.setClassname(commandLineClass.getName());

        for (Iterator iterator = targetArgumentList.iterator(); iterator.hasNext();) {
            String className = iterator.next().toString();
            commandLine.createArgument().setLine(className);
        }

        if (run() != 0) {
            throw new BuildException("Command execution FAILED");
        }
        log("Command execution passed");
    }

    protected String[] getArguments(FileSet fileset, FilesetParser parser) {
        return parser.getClassNames(fileset, getProject());
    }

    public void addFilesetTarget(FileSet fileset) {
        filesets.add(fileset);
    }

    private String locate() {
        URL url = getClass().getResource(resourcePathToClassFile());
        if ("jar".equalsIgnoreCase(url.getProtocol())) {
            return getJarFileOnClassPath(url);
        } else {
            return goToClassPathRootDirectory(url);
        }
    }

    private String goToClassPathRootDirectory(URL url) {
        File classFile = getFileOnClassPath(url);
        int level = new StringTokenizer(getClass().getName(), ".").countTokens();
        File directory = classFile.getParentFile();
        for (int i = 0; i < level - 1; i++) {
            directory = directory.getParentFile();
        }
        return directory.getAbsolutePath();
    }

    private String getJarFileOnClassPath(URL url) {
        String file = url.getFile();
        int index = file.indexOf("!");
        if (index == -1) {
            throw new IllegalArgumentException(url.toExternalForm() + " does not have '!' for a Jar URL");
        }
        File fileObject = fromUri(file.substring(0, index));
        return fileObject.getAbsolutePath();
    }

    private File getFileOnClassPath(URL url) {
        return fromUri(url.getFile());
    }

    private File fromUri(String fileUri) {
        try {
            return new File(new URI(fileUri));
        } catch (URISyntaxException e) {
            throw new RuntimeException("Couldn't locate file:" + fileUri, e);
        }
    }

    private String resourcePathToClassFile() {
        return "/" + getClass().getName().replace('.', '/') + ".class";
    }
}
