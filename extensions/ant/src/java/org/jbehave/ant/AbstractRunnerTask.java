package org.jbehave.ant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sf.cotta.utils.ClassPath;
import net.sf.cotta.utils.ClassPathLocator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;

public class AbstractRunnerTask extends AbstractJavaTask {

    private final Class runnerClass;
    private final FilesetParser filesetParser;
    private List targetClassList = new LinkedList();
    private List filesets =  new ArrayList();
    
    public AbstractRunnerTask(Class runnerClass, CommandRunner runner, FilesetParser filesetParser) {
        super(runner);
        this.runnerClass = runnerClass;
        this.filesetParser = filesetParser;
    }

    public void addTarget(String targetClass) {
        targetClassList.add(targetClass);
    }
    
    
    public void execute() {
        appendAntTaskJar();
        invoke();
    }
    

    private void appendAntTaskJar() {
        ClassPath classPath = new ClassPathLocator(getClass()).locate();
        createClasspath().append(new Path(getProject(), classPath.path()));
    }
    
    private void invoke() {
        
        for (Iterator iter = filesets.iterator(); iter.hasNext();) {
            FileSet fileset = (FileSet) iter.next();
               
                String[] classNames = filesetParser.getClassNames(fileset, getProject());
                
                for (int i = 0; i < classNames.length; i++) {
                    addTarget(classNames[i]);                    
                }
        }
        
        commandLine.setClassname(runnerClass.getName());
        
        for (Iterator iterator = targetClassList.iterator(); iterator.hasNext();) {
            String className = iterator.next().toString();
            commandLine.createArgument().setLine(className);
        }
        
        if (run() != 0) {
            throw new BuildException("behaviour verification FAILED");
        }
        log("Behaviours verification passed");
    }

    public void addFilesetTarget(FileSet fileset) {
        filesets.add(fileset);
    }
}
