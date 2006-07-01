package jbehave.core.util;

import jbehave.core.minimock.UsingConstraints;
import net.sf.cotta.TDirectory;

public class ClassPathLocatorBehaviour extends UsingConstraints {
    public void shouldLocateClassInDirectory() throws Exception {
        TDirectory directory = new ClassPathLocator(ClassPathLocator.class).locateClassPathRoot();
        ensureThat(directory.dir("jbehave").dir("core").dir("util").file("ClassPathLocatorBehaviour.class").exists(),
                eq(true));
    }
}
