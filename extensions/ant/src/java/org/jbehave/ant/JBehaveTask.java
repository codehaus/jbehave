/*
 * Created on 19-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for licence details
 */
package org.jbehave.ant;

import org.apache.tools.ant.types.FileSet;
import org.jbehave.core.Run;

public class JBehaveTask extends AbstractRunnerTask {

    public JBehaveTask() {
        this(new CommandRunnerImpl(), new TrimFilesetParser());
    }

    public JBehaveTask(CommandRunner runner, FilesetParser filesetParser) {
        super(Run.class, runner, filesetParser);
    }

    public void setBehavioursClassName(String behavioursClassName) {
        super.addTarget(behavioursClassName);
    }
    
    public void addBehaviours(FileSet fileset) {
        super.addFilesetTarget(fileset);
    }

}
