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
import jbehave.framework.CriteriaVerification;
import jbehave.framework.CriteriaVerifier;
import jbehave.verify.listener.CompositeListener;
import jbehave.verify.listener.Listener;

/**
 * @author <a href="mailto:dan@jbehave.org">Dan North</a>
 */
public class Verifier {
    private final List specs = new ArrayList();
    private final Map criteriaMap = new HashMap() {
        public Object get(Object key) {
            Object result = super.get(key);
            return result == null ? new ArrayList() : result;
        }
        public Object put(Object key, Object value) {
            List values = (List)get(key);
            values.add(value);
            super.put(key, values);
            return values;
        }
    };
    private final CompositeListener listeners = new CompositeListener();
    private int criteriaCount = 0;

    public void addSpec(Class spec) {
        Collection criteriaVerifiers = new CriteriaExtractor(spec).createCriteriaVerifiers();
        for (Iterator i = criteriaVerifiers.iterator(); i.hasNext();) {
            CriteriaVerifier currentCriteria = (CriteriaVerifier)i.next();
            Class currentSpec = currentCriteria.getSpecInstance().getClass();
            if (!specs.contains(currentSpec)) {
                specs.add(currentSpec);
            }
            criteriaMap.put(currentSpec, currentCriteria);
        }
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
        for (Iterator specsIterator = specs.iterator(); specsIterator.hasNext();) {
            final Class spec = (Class)specsIterator.next();
            listeners.specVerificationStarted(spec);
            final Collection criteria = (Collection)criteriaMap.get(spec);
            for (Iterator criteriaIterator = criteria.iterator(); criteriaIterator.hasNext();) {
                final CriteriaVerifier verifier = (CriteriaVerifier)criteriaIterator.next();
                listeners.beforeCriteriaVerificationStarts(verifier);
                CriteriaVerification verification = verifier.verifyCriteria();
                listeners.afterCriteriaVerificationEnds(verification);
            }
            listeners.specVerificationEnded(spec);
        }
        listeners.verificationEnded(this);
    }
}
