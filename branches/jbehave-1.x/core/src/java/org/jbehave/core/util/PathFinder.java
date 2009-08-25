package org.jbehave.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;

/**
 * A utility path finder
 * 
 * @author Mauro Talevi
 */
public class PathFinder {

    /**
     * Scanner used to list relative paths
     */
    private DirectoryScanner scanner = new DirectoryScanner();

    /**
     * Lists paths from a basedir, allowing for includes/excludes
     * 
     * @param basedir the basedir path
     * @param rootPath the root path prefixed to all paths found, or
     *            <code>null</code> if none
     * @param includes the List of include patterns, or <code>null</code> if
     *            none
     * @param excludes the List of exclude patterns, or <code>null</code> if
     *            none
     * @return A List of paths
     */
    public List listPaths(String basedir, String rootPath, List includes, List excludes) {
        scanner.setBasedir(basedir);
        if (includes != null) {
            scanner.setIncludes((String[]) includes.toArray(new String[includes.size()]));
        }
        if (excludes != null) {
            scanner.setExcludes((String[]) excludes.toArray(new String[excludes.size()]));
        }
        scanner.scan();
        List paths = new ArrayList();
        String basePath = (rootPath != null ? rootPath + "/" : "");
        List relativePaths = Arrays.asList(scanner.getIncludedFiles());
        for (Iterator i = relativePaths.iterator(); i.hasNext();) {
            String relativePath = (String) i.next();
            String path = basePath + relativePath;
            paths.add(path);
        }
        return paths;
    }

}
