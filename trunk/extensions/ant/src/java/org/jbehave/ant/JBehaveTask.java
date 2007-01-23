/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jbehave.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sf.cotta.utils.ClassPath;
import net.sf.cotta.utils.ClassPathLocator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.jbehave.core.Run;

public class JBehaveTask extends AbstractJavaTask {
    private List behaviourClassList = new LinkedList();
    private List filesets = new ArrayList();
    private final FilesetParser filesetParser;

    public JBehaveTask() {
        super();
        this.filesetParser = new TrimFilesetParser();
    }

    public JBehaveTask(CommandRunner runner, FilesetParser filesetParser) {
        super(runner);
        this.filesetParser = filesetParser;
    }

    public BehaviourClassDetails createVerify() {
        BehaviourClassDetails details = new BehaviourClassDetails();
        behaviourClassList.add(details);
        return details;
    }

    public void setBehavioursClassName(String behavioursClassName) {
        BehaviourClassDetails details = new BehaviourClassDetails();
        details.setName(behavioursClassName);
        behaviourClassList.add(details);
    }
    
    public void execute() {
        validateElements();
        appendAntTaskJar();
        invoke();
    }

    private void appendAntTaskJar() {
        ClassPath classPath = new ClassPathLocator(getClass()).locate();
        createClasspath().append(new Path(getProject(), classPath.path()));
    }

    private void validateElements() {
    }

    private void invoke() {
        
        for (Iterator iter = filesets.iterator(); iter.hasNext();) {
            FileSet fileset = (FileSet) iter.next();
            

               
                String[] classNames = filesetParser.getClassNames(fileset, getProject());
                for (int i = 0; i < classNames.length; i++) {
                    setBehavioursClassName(classNames[i]);                    
                }
            
        }
        
        commandLine.setClassname(Run.class.getName());
        for (Iterator iterator = behaviourClassList.iterator(); iterator.hasNext();) {
            BehaviourClassDetails behaviourClassDetail = (BehaviourClassDetails) iterator.next();
            commandLine.createArgument().setLine(behaviourClassDetail.getName());
        }
        

        
        if (run() != 0) {
            throw new BuildException("behaviour verification FAILED");
        }
        log("Behaviours verification passed");
    }
    
    
    public void addBehaviours(FileSet fileset) {
        filesets.add(fileset);
                
    }

}
