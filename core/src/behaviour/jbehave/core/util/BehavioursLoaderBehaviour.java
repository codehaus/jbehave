package jbehave.core.util;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jbehave.core.Run;
import jbehave.core.minimock.Constraint;
import jbehave.core.minimock.MockObjectBehaviour;
import jbehave.core.minimock.UsingConstraints;
import jbehave.core.story.listener.PlainTextListenerBehaviour;

public class BehavioursLoaderBehaviour extends UsingConstraints {

    public void shouldLoadPublicBehaviourEndedWithBehaviourButNotAbstract() throws Exception {
        BehavioursLoader loader = new BehavioursLoader(getClass());
        Set loadedFromBehaviourLoader = new HashSet(Arrays.asList(loader.loadBehaviours()));
        ensureThatBehavioursAreLoaded(loadedFromBehaviourLoader);
    }

    public void shouldLoadBehaviourUsingCustomClassLoader() throws Exception {
        BehavioursLoader loader = new BehavioursLoader(getClass(), new CustomClassLoader(false));
        Set loadedFromBehaviourLoader = new HashSet(Arrays.asList(loader.loadBehaviours()));
        ensureThatBehavioursAreLoaded(loadedFromBehaviourLoader);
    }

    public void shouldNotLoadBehaviourUsingInvalidCustomClassLoader() throws Exception {
        BehavioursLoader loader = new BehavioursLoader(getClass(), new CustomClassLoader(true));
        Set loadedFromBehaviourLoader = new HashSet();
        try {
            loadedFromBehaviourLoader = new HashSet(Arrays.asList(loader
                    .loadBehaviours()));
        } catch ( Exception e) {
            ensureThat(loadedFromBehaviourLoader, setEmpty());
        }        
    }
    
    private void ensureThatBehavioursAreLoaded(Set loadedFromBehaviourLoader) {
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

    private Constraint setEmpty() {
        return new Constraint() {
            public boolean matches(Object arg) {
                return ((Set) arg).isEmpty();
            }

            public String toString() {
                return "collection is empty";
            }
        };
    }

    
    private static class CustomClassLoader extends ClassLoader {        
        boolean invalid;        
        public CustomClassLoader(boolean invalid) {
            this.invalid = invalid;
        }
        public Class findClass(String name) throws ClassNotFoundException{
            if ( invalid ){
                throw new ClassNotFoundException();
            }
            return BehavioursLoader.class.getClassLoader().loadClass(name);            
        }
    }
}
