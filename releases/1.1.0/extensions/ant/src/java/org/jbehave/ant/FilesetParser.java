package org.jbehave.ant;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

public interface FilesetParser {

    String[] getClassNames(FileSet fileset, Project project);

}
