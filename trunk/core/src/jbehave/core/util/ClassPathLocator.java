package jbehave.core.util;

import net.sf.cotta.TDirectory;
import net.sf.cotta.TFile;
import net.sf.cotta.TFileFactory;
import net.sf.cotta.zip.ZipFileSystem;

import java.net.*;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class ClassPathLocator {
    private Class clazz;

    public ClassPathLocator(Class clazz) {
        this.clazz = clazz;
    }

    public TDirectory locateClassPathRoot() {
        URL url = getClass().getResource(resourcePathToClassFile());
        if ("jar".equalsIgnoreCase(url.getProtocol())) {
            return getJarRoot(url);
        }
        else {
            TFile classFile = getClassFile(url);
            int level = new StringTokenizer(clazz.getName(), ".").countTokens();
            TDirectory directory = classFile.parent();
            for (int i = 0; i < level - 1; i++) {
                directory = directory.parent();
            }
            return directory;
        }
    }

    private String resourcePathToClassFile() {
        return "/" + clazz.getName().replace('.', '/') + ".class";
    }

    private TDirectory getJarRoot(URL url) {
        String file = url.getFile();
        int index = file.indexOf("!");
        if (index == -1) {
            throw new IllegalArgumentException(url.toExternalForm() + " does not have '!' for a Jar URL");
        }
        File jarFile = null;
        try {
            jarFile = new File(new URI(file.substring(0, index)));
            return new TFileFactory(ZipFileSystem.readOnlyZipFileSystem(jarFile)).dir("/");
        } catch (URISyntaxException e) {
            throw new RuntimeException("Couldn't convert url to jar file");
        } catch (IOException e) {
            throw new RuntimeException("Coun't read jar file: " + url);
        }
    }

    private TFile getClassFile(URL url) {
        try {
            File file = new File(new URI(url.toExternalForm()));
            return new TFileFactory().file(file.getAbsolutePath());
        } catch (URISyntaxException e) {
            throw new RuntimeException("Couldn't convert URL to File:" + url, e);
        }
    }
}
