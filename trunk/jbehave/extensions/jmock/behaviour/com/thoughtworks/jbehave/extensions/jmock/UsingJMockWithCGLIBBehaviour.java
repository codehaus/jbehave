/*
 * Created on 20-Sep-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package com.thoughtworks.jbehave.extensions.jmock;

import java.lang.reflect.Proxy;

import com.thoughtworks.jbehave.core.responsibility.Verify;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class UsingJMockWithCGLIBBehaviour {
    
    public static interface AnInterface {}
    public static abstract class AnAbstractClass implements AnInterface {}
    public static class AConcreteClass extends AnAbstractClass {}
    
    public class BehaviourClass extends UsingJMockWithCGLIB {
        public Mock anInterface;
        public Mock anAbstractClass;
        public Mock aConcreteClass;
        
        public void shouldDoSomething() {
            anInterface = new Mock(AnInterface.class);
            anAbstractClass = new Mock(AnAbstractClass.class);
            aConcreteClass = new Mock(AConcreteClass.class);
        }
    }
    
    private boolean isDynamicProxy(Object proxy) {
        return Proxy.isProxyClass(proxy.getClass());
    }

    public void shouldCreateRegularMockIfAndOnlyIfMockingInterface() throws Exception {
        // given...
        BehaviourClass instance = new BehaviourClass();
        
        // when...
        instance.shouldDoSomething();
        
        // verify...
        Verify.that(isDynamicProxy(instance.anInterface.proxy()));
        Verify.not(isDynamicProxy(instance.anAbstractClass.proxy()));
        Verify.not(isDynamicProxy(instance.aConcreteClass.proxy()));
        
    }
}
