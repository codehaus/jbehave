package org.jbehave.core.util;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.jbehave.core.listener.PlainTextListenerBehaviourSupport;
import org.jbehave.core.minimock.MiniMockObjectBehaviour;
import org.jbehave.core.mock.Matcher;
import org.jbehave.core.mock.UsingMatchers;


public class BehavioursLoaderBehaviour extends UsingMatchers {

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
        ensureThat(loadedFromBehaviourLoader, setContains(MiniMockObjectBehaviour.class));
        ensureThat(Modifier.isAbstract(PlainTextListenerBehaviourSupport.class.getModifiers()), eq(true));
        ensureThat(loadedFromBehaviourLoader, not(setContains(PlainTextListenerBehaviourSupport.class)));
    }

    private Matcher setContains(final Object item) {
        return new Matcher() {
            public boolean matches(Object arg) {
                return ((Set) arg).contains(item);
            }

            public String toString() {
                return "collection contains <" + item + ">";
            }
        };
    }

    private Matcher setEmpty() {
        return new Matcher() {
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
