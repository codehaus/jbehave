package jbehave.core.util;

import jbehave.core.minimock.Constraint;
import jbehave.core.minimock.MockObjectBehaviour;
import jbehave.core.minimock.UsingConstraints;
import jbehave.core.story.listener.PlainTextListenerBehaviour;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.lang.reflect.Modifier;

public class BehavioursLoaderBehaviour extends UsingConstraints {
    public void shouldLoadPublicBehaviourEndedWithBehaviourButNotAbstract() throws Exception {
        Set loadedFromBehaviourLoader = new HashSet(Arrays.asList(new BehavioursLoader(getClass()).loadBehaviours()));
        ensureThat(loadedFromBehaviourLoader, setContains(MockObjectBehaviour.class));
        ensureThat(Modifier.isAbstract(PlainTextListenerBehaviour.class.getModifiers()), eq(true));
        ensureThat(loadedFromBehaviourLoader, not(setContains(PlainTextListenerBehaviour.class)));
    }

    private Constraint setContains(final Object item) {
        return new Constraint() {
            public boolean matches(Object arg) {
                return ((Set) arg).contains(item);
            }

            public String toString() {
                return "collection contains <" + item + ">";
            }
        };
    }
}
