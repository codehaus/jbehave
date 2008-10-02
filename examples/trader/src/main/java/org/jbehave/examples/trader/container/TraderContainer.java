package org.jbehave.examples.trader.container;

import org.jbehave.container.pico.XMLPicoContainer;


//FIXME there are classloading issues (only using maven plugin) if TraderContainer extends the XMLPicoContainer cointained in core
//If the scenarios are run as Ant tasks, injecting in the taskdef the maven.runtime.classpath, it works fine
//Not quite sure what the root cause is - need more investigation. 
public class TraderContainer extends XMLPicoContainer {

    public TraderContainer(ClassLoader classLoader) {
        super("org/jbehave/examples/trader/container/trader.xml", classLoader);
    }
}
