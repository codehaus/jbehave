package org.jbehave.examples.trader.container;

import org.jbehave.container.pico.XMLPicoContainer;


//FIXME there are classloading issues (only using maven plugin) if TraderContainer extends the XMLPicoContainer cointained in core
//Not quite sure what the root cause is - need more investigation. 
//NOTE separating jbehave-core from jbehave-container jar fixes the problem!  Possible issue with classloader hierarchy?  Could d
//it bite somewhere else too - eg in a webapp container?
public class TraderContainer extends XMLPicoContainer {

    public TraderContainer(ClassLoader classLoader) {
        super("org/jbehave/examples/trader/container/trader.xml", classLoader);
    }
}
