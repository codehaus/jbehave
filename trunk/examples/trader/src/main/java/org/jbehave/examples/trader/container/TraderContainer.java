package org.jbehave.examples.trader.container;


//FIXME there are classloading issues (only using maven plugin) if TraderContainer extends the XMLPicoContainer cointained in core
//Not quite sure what the root cause is - need more investigation. 
public class TraderContainer extends XMLContainer {

    public TraderContainer(ClassLoader classLoader) {
        super("org/jbehave/examples/trader/container/trader.xml", classLoader);
    }
}
