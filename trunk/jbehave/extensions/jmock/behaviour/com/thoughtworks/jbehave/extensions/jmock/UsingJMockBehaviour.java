package com.thoughtworks.jbehave.extensions.jmock;

import java.lang.reflect.Proxy;

import org.jmock.Mock;
import org.jmock.core.Verifiable;

import com.thoughtworks.jbehave.core.exception.VerificationException;
import com.thoughtworks.jbehave.core.verify.Verify;

/**
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy</a>
 * @author <a href="mailto:dnorth@thoughtworks.com">Dan North</a>
 */
public class UsingJMockBehaviour extends JMockSugar {
    
    public interface Interface {
        void doStuff();
    }
    
    public static class BehaviourClassUsingJMock extends UsingJMock {
        public void shouldDoSomething() {
            new Mock(Interface.class);
            new Mock(Interface.class);
        }
    }
    
    public void shouldStoreEachMockAsItIsConstructed() throws Exception {
        // given...
        BehaviourClassUsingJMock instance = new BehaviourClassUsingJMock();
        
        // when...
        instance.shouldDoSomething();
        
        // then...
        Verify.equal(2, instance.getMocks().size());
        Verify.that(instance.containsMocks());
        Verify.that(instance.getMocks().get(0) instanceof org.jmock.Mock);
        Verify.that(instance.getMocks().get(1) instanceof org.jmock.Mock);
    }
    
    public static class BehaviourClassWithoutMocks extends UsingJMock {
        public void shouldDoSomething() throws Exception {
        }
    }
    
    public void shouldBeEmptyIfNoMocksWereCreated() throws Exception {
        // given...
        BehaviourClassWithoutMocks instance = new BehaviourClassWithoutMocks();

        // when...
        instance.shouldDoSomething();
        
        // then...
        Verify.equal(0, instance.getMocks().size());
        Verify.not(instance.containsMocks());
    }
    
    /**
     * Ok, you'll need to concentrate here. This class is used to verify the behaviour of {@link UsingJMock#verify()}.
     * To do this we have to mock the {@link Verifiable} interface, to ensure that its <tt>verify()</tt> method is called.
     * 
     * Got that? We're going to mock a Mock. I hope it doesn't get any more self-referential than this because my
     * brain might implode. God alone knows how they wrote JMock without going insane.
     */
    public static class BehaviourClassWithMockMocks extends UsingJMock {
        public BehaviourClassWithMockMocks(Verifiable mock1, Verifiable mock2) {
            mocks.add(mock1);
            mocks.add(mock2);
        }
    }
    
    public void shouldVerifyMocks() throws Exception {
        // given...
        Mock mock1 = new Mock(Verifiable.class);
        Mock mock2 = new Mock(Verifiable.class);
        UsingJMock instance =
            new BehaviourClassWithMockMocks((Verifiable)mock1.proxy(), (Verifiable) mock2.proxy());
        
        // expect...
        mock1.expects(once()).method("verify").withNoArguments();
        mock2.expects(once()).method("verify").withNoArguments();
        
        // when...
        instance.verify();
        
        // verify...
        mock1.verify();
        mock2.verify();
    }
    
    public static class HasMockThatFailsVerify extends UsingJMock {
        {
            new Mock(Interface.class).expects(once()).method("doStuff").withNoArguments();
        }
    }
    
    public void shouldWrapJMockVerificationFailureAsVerificationException() throws Exception {
        // given...
        UsingJMock instance = new HasMockThatFailsVerify();

        // when...
        try {
            instance.verify();
            Verify.impossible("should have thrown VerificationException");
        }
        catch (VerificationException expected) {
        }
    }
    
    public static interface AnInterface {}
    public static abstract class AnAbstractClass implements AnInterface {}
    public static class AConcreteClass extends AnAbstractClass {}
    
    public class BehaviourClass extends UsingJMock {
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
