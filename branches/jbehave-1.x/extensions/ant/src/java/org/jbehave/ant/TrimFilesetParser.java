package org.jbehave.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

public class TrimFilesetParser implements FilesetParser {

    public String[] getClassNames(FileSet fileset, Project project) {
        String[] relativePaths = getRelativePaths(fileset, project);
        List classnames = new ArrayList();
        for (int i = 0; i < relativePaths.length; i++) {
            classnames.add(stripFileExtension(relativePaths[i].replace('/', '.')));
        }
        return (String[]) classnames.toArray(new String[classnames.size()]);
    }

    public String[] getRelativePaths(FileSet fileset, Project project) {
        DirectoryScanner ds = fileset.getDirectoryScanner(project);
        String[] includedFiles = ds.getIncludedFiles();
        File base = ds.getBasedir();
        List paths = new ArrayList();
        for (int i = 0; i < includedFiles.length; i++) {
            paths.add(findRelativePath(base, includedFiles[i]));
        }
        return (String[]) paths.toArray(new String[paths.size()]);
    }

    private String findRelativePath(File base, String filePath) {
        File found = new File(base, filePath.replace('\\', '/'));
        return found.getAbsolutePath().substring(base.getAbsolutePath().length() + 1).replace('\\', '/');
    }

    private String stripFileExtension(String classname) {
        if (classname.length() > 6 && classname.substring(classname.length() - 6, classname.length()).equals(".class")) {
            classname = classname.substring(0, classname.length() - 6);
        } else if (classname.length() > 5
                && classname.substring(classname.length() - 5, classname.length()).equals(".java")) {
            classname = classname.substring(0, classname.length() - 5);
        }
        return classname;
    }

}
