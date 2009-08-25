package org.jbehave.core.mock;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.Block;
import org.jbehave.core.exception.PendingException;
import org.jbehave.core.exception.VerificationException;


public class UsingMatchersBehaviour {

    public void shouldDelegateCorrectlyAndNotThrowAnyStackOverflowErrors() throws Exception {
        final UsingMatchers m = new UsingMatchers(){};
        Object obj = new Object();
        
        m.ensureThat(true);
        m.ensureThat(true, "works with a message");
        m.ensureThat(false, m.eq(false));
        m.ensureThat('a', m.eq('a'));
        m.ensureThat(1.0, m.eq(1.0));
        m.ensureThat(1L, m.eq(1L));
        m.ensureThat(obj, m.is(obj));
        m.ensureThat(false, m.not(m.eq(true)), "works with a message");
        m.ensureThat('c', m.eq('a').or(m.eq('c')), "works with a message");
        m.ensureThat(1.5, m.eq(1.5), "works with a message");
        m.ensureThat(3L, m.and(m.eq(3L), m.isA(Long.class)), "works with a message");
        m.ensureThat(new Double(4.3), m.isA(Double.class), "works with a message");
        m.ensureThat(null, m.isNull());
        m.ensureThat(obj, m.not(m.nothing()));
        
        Exception e = m.runAndCatch(VerificationException.class, new Block() {
			public void run() throws Exception {
				m.fail("Oops!");
			}
        });
        m.ensureThat(e, m.isNotNull());
        
        e = m.runAndCatch(PendingException.class, new Block() {
			public void run() throws Exception {
				m.todo();
			}
        });
        m.ensureThat(e, m.isNotNull());
        
        String[] array = new String[]{"cow", "horse"};
        List list = Arrays.asList(array);
		m.ensureThat(array, m.arrayContaining("cow"));
		m.ensureThat(array, m.arrayContainingOnly("horse", "cow"));
		m.ensureThat(array, m.arrayContainingInOrder("cow", "horse"));		
		m.ensureThat(list, m.collectionContaining("cow"));
		m.ensureThat(list, m.collectionContainingOnly("horse", "cow"));
		m.ensureThat(list, m.collectionContainingInOrder("cow", "horse"));
		m.ensureThat(array, m.arrayContaining(m.endsWith("ow")));
		m.ensureThat(list, m.collectionContaining(m.startsWith("hor")));
    }  
}
