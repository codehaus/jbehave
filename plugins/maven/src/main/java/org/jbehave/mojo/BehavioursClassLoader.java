package org.jbehave.mojo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbehave.core.behaviour.Behaviours;

/**
 * Extends URLClassLoader to instantiate Behaviours.
 * 
 * @author Mauro Talevi
 */
public class BehavioursClassLoader extends URLClassLoader {

    public BehavioursClassLoader(List classpathElements)
            throws MalformedURLException {
        super(toClasspathURLs(classpathElements), Behaviours.class
                .getClassLoader());
    }

    public BehavioursClassLoader(List classpathElements, ClassLoader parent)
            throws MalformedURLException {
        super(toClasspathURLs(classpathElements), parent);
    }

    /**
     * Loads and instantiates a Behaviours class
     * 
     * @param behavioursName the name of the Behaviours
     * @return A Behaviours instance
     * @throws IllegalAccessException
     */
    public Behaviours newBehaviours(String behavioursName)
            throws InstantiationException, IllegalAccessException {
        String behavioursNotFound = "The behaviours " + behavioursName
                + " was not found in " + toString(getURLs());
        try {
            Behaviours behavious = (Behaviours) loadClass(behavioursName).newInstance();
            Thread.currentThread().setContextClassLoader(this);
            return behavious;
        } catch (ClassCastException e) {
            throw new RuntimeException(behavioursName + " is not a "
                    + Behaviours.class.getName(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(behavioursNotFound, e);
        } catch (NoClassDefFoundError e) {
            throw new RuntimeException(behavioursNotFound, e);
        }
    }

    private String toString(URL[] urls) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < urls.length; i++) {
            buffer.append(urls[i].toString());
            if (i < urls.length - 1) {
                buffer.append(", ");
            }
        }
        return buffer.toString();
    }

    protected static URL[] toClasspathURLs(List classpathElements)
            throws MalformedURLException {
        List urls = new ArrayList();
        if (classpathElements != null) {
            for (Iterator i = classpathElements.iterator(); i.hasNext();) {
                String classpathElement = (String) i.next();
                urls.add(new File(classpathElement).toURL());
            }
        }
        return (URL[]) urls.toArray(new URL[urls.size()]);
    }

}
