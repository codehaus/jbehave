package jbehave.jmock;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jbehave.core.Block;
import jbehave.core.Ensure;
import jbehave.core.exception.VerificationException;
import jbehave.core.mock.UsingMatchers;

import org.jmock.Mock;
import org.jmock.core.Verifiable;


/**
 * @author <a href="mailto:dguy@thoughtworks.com">Damian Guy</a>
 * @author <a href="mailto:dnorth@thoughtworks.com">Dan North</a>
 */
public class UsingJMockBehaviour extends UsingMatchers {
    
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
        ensureThat(instance.getMocks().size(), eq(2));
        ensureThat(instance.containsMocks(), eq(true), "instance contains mocks");
        ensureThat(instance.getMocks().get(0), isA(org.jmock.Mock.class));
        ensureThat(instance.getMocks().get(1), isA(org.jmock.Mock.class));
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
        ensureThat(instance.getMocks().isEmpty(), eq(true), "isEmpty");
        ensureThat(instance.containsMocks(), eq(false), "containsMocks");
    }
    
    /**
     * Ok, you'll need to concentrate here. This class is used to verify the behaviour of {@link UsingJMock#verifyMocks()}.
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
    	JMockSugar s = new JMockSugar() {};
        Mock mock1 = new Mock(Verifiable.class);
        Mock mock2 = new Mock(Verifiable.class);
        UsingJMock instance =
            new BehaviourClassWithMockMocks((Verifiable)mock1.proxy(), (Verifiable) mock2.proxy());
        
        // expect...
        mock1.expects(s.once()).method("verify").withNoArguments();
        mock2.expects(s.once()).method("verify").withNoArguments();
        
        // when...
        instance.verifyMocks();
        
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
        final UsingJMock instance = new HasMockThatFailsVerify();

        // when...
        ensureThrows(VerificationException.class, new Block() {
            public void run() throws Exception {
                instance.verifyMocks();
            }
        });
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
        Ensure.that(isDynamicProxy(instance.anInterface.proxy()));
        Ensure.not(isDynamicProxy(instance.anAbstractClass.proxy()));
        Ensure.not(isDynamicProxy(instance.aConcreteClass.proxy()));
    }
	
	public void shouldVerifyMocksAfterEachMethod() throws Exception {
		// given
		final Object[] results = new Object[1];
		UsingJMock instance = new UsingJMock() {
			public void verifyMocks() {
				results[0] = "verifyMocks";
			}
		};

		// when
		instance.verify();
		
		// then
		ensureThat(results[0], eq("verifyMocks"));
	}
	
	public void shouldCallTemplateMethodAfterVerifyingMocks() throws Exception {
		// given
		final List results = new ArrayList();
		UsingJMock instance = new UsingJMock() {
			public void verifyMocks() {
				results.add("verifyMocks");
			}
			public void doVerify() {
				results.add("doVerify");
			}
		};
		List expected = Arrays.asList(new String[] {
				"doVerify", "verifyMocks"
		});

		// when
		instance.verify();
		
		// then
		ensureThat(results, eq(expected));
	}
}
