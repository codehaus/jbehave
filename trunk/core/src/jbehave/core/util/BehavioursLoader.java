package jbehave.core.util;

import net.sf.cotta.TDirectory;
import net.sf.cotta.TIoException;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Modifier;

public class BehavioursLoader {
    private Class markerClass;

    public BehavioursLoader(Class markerClass) {
        this.markerClass = markerClass;
    }

    public Class[] getBehaviours() {
        markerClass = BehavioursLoader.class;
        TDirectory directory = new ClassPathLocator(markerClass).locateClassPathRoot();
        List classNames = null;
        try {
            classNames = new BehaviourCollector(directory, "").collectNames();
        } catch (TIoException e) {
            throw new RuntimeException(e);
        }
        return convertToClasses(classNames);
    }

    private Class[] convertToClasses(List classNames) {
        List classes = new ArrayList(classNames.size());
        for (int i = 0; i < classNames.size(); i++) {
            String name = (String) classNames.get(i);
            Class behaviourClass = null;
            try {
                behaviourClass = Class.forName(name);
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