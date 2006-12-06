package org.jbehave.core.util;

import net.sf.cotta.TDirectory;
import net.sf.cotta.TIoException;
import net.sf.cotta.utils.ClassPathLocator;
import net.sf.cotta.utils.ClassPath;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Modifier;

/**
 * Loads behaviour classes, using injected ClassLoader.  If none provided,
 * the current context ClassLoader is used.
 * 
 * @author Mauro Talevi
 */
public class BehavioursLoader {
    private Class markerClass;
    private ClassLoader classLoader;

    public BehavioursLoader(Class markerClass) {
        this(markerClass, Thread.currentThread().getContextClassLoader());
    }

    public BehavioursLoader(Class markerClass, ClassLoader classLoader) {
        this.markerClass = markerClass;
        this.classLoader = classLoader;
    }
    
    public Class[] loadBehaviours() {
        ClassPathLocator classPathLocator = new ClassPathLocator(markerClass);
        ClassPath classPath = classPathLocator.locate();
        TDirectory directory = null;
        try {
            directory = classPath.asDirectory();
        } catch (TIoException e) {
            throw new RuntimeException("Error locating classpath contains <" + markerClass.getName() + ">", e);
        }
        List classNames = null;
        try {
            classNames = new BehaviourCollector(directory, "").collectNames();
        } catch (TIoException e) {
            throw new RuntimeException(e);
        }
        finally {
            closeResource(classPath);
        }
        return convertToClasses(classNames);
    }

    private void closeResource(ClassPath classPathLocator) {
        try {
            classPathLocator.closeResource();
        } catch (TIoException e) {
            throw new RuntimeException(e);
        }
    }

    private Class[] convertToClasses(List classNames) {
        List classes = new ArrayList(classNames.size());
        for (int i = 0; i < classNames.size(); i++) {
            String name = (String) classNames.get(i);
            Class behaviourClass = null;
            try {
                behaviourClass = classLoader.loadClass(name);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Cannot load class:" + name);
            }
            if (Modifier.isPublic(behaviourClass.getModifiers()) &&
                    !Modifier.isAbstract(behaviourClass.getModifiers())) {
                classes.add(behaviourClass);
            }
        }
        return (Class[]) classes.toArray(new Class[classes.size()]);
    }

}
