/*
 * Created on 25-Dec-2003
 * 
 * (c) 2003-2004 ThoughtWorks
 * 
 * See license.txt for license details
 */
package jbehave.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jbehave.framework.CriteriaExtractor;
import jbehave.framework.CriteriaVerificationResult;
import jbehave.framework.CriteriaVerifier;
import jbehave.verify.listener.CompositeListener;
import jbehave.verify.listener.Listener;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class Verifier {
    private final List specs = new ArrayList();
    private final Map criteriaMap = new HashMap();
    private final CompositeListener listeners = new CompositeListener();
    private int criteriaCount = 0;

    public void addBehaviourClass(Class spec) {
        Collection criteriaVerifiers = new CriteriaExtractor(spec).extractCriteria();
        specs.add(spec);
        criteriaMap.put(spec, criteriaVerifiers);
        criteriaCount += criteriaVerifiers.size();
    }
    
    public int countSpecs() {
        return specs.size();
    }
    
    public int countCriteria() {
        return criteriaCount;
    }

    public Class getSpec(int i) {
        return (Class)specs.get(i);
    }
    
    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void verifyCriteria() {
        listeners.verificationStarted(this);
        for (Iterator i = specs.iterator(); i.hasNext();) {
            final Class spec = (Class)i.next();
            listeners.specVerificationStarted(spec);
            
            final Collection criteria = (Collection)criteriaMap.get(spec);
            for (Iterator j = criteria.iterator(); j.hasNext();) {
                final CriteriaVerifier verifier = (CriteriaVerifier)j.next();
                listeners.beforeCriteriaVerificationStarts(verifier);
                CriteriaVerificationResult result = verifier.verify();
                listeners.afterCriteriaVerificationEnds(result);
            }
            listeners.specVerificationEnded(spec);
        }
        listeners.verificationEnded(this);
    }
}
