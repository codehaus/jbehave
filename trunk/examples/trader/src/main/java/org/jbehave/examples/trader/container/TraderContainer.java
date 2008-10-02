package org.jbehave.examples.trader.container;

import org.jbehave.container.pico.XMLPicoContainer;


//FIXME there are classloading issues (using maven plugin) if TraderContainer extends the XMLPicoContainer cointained in core
//while it's got no issues to load a class in this module (eg XMLContainer) which uses the same dependencies as XMLPicoContainer
//NOTE: If the scenarios are run as Ant tasks, injecting in the taskdef the maven.runtime.classpath, it works fine
//The difference is that the taskdef uses the Ant classloader Delegate (cf org.apache.tools.ant.util.ClasspathUtils#Delegate) 
//and not the ScenarioClassLoader constructed from the specified classpath elements
//TODO understand the difference in the classloaders created
public class TraderContainer extends XMLPicoContainer {

    public TraderContainer(ClassLoader classLoader) {
        super("org/jbehave/examples/trader/container/trader.xml", classLoader);
    }
}
