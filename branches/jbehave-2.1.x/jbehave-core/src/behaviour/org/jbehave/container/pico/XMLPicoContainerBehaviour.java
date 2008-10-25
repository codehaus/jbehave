package org.jbehave.container.pico;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.jbehave.container.AComponent;
import org.jbehave.container.AnotherComponent;
import org.jbehave.container.ComponentNotFoundException;
import org.jbehave.container.Container;
import org.jbehave.container.InvalidContainerException;
import org.junit.Test;

/**
 * @author Mauro Talevi
 */
public class XMLPicoContainerBehaviour {

    @Test
    public void canGetComponentByKey() {
        Container container = new XMLPicoContainer("org/jbehave/container/pico/components.xml");
        assertNotNull(container.getComponent(AComponent.class, "a-component"));
    }

    @Test(expected = ComponentNotFoundException.class)
    public void cannotGetComponentByInexistentKey() {
        Container container = new XMLPicoContainer("org/jbehave/container/pico/components.xml");
        container.getComponent(AComponent.class, "inexistent-key");
    }

    @Test
    public void canGetComponentByType() {
        Container container = new XMLPicoContainer("org/jbehave/container/pico/components.xml");
        assertNotNull(container.getComponent(AnotherComponent.class));
    }

    @Test
    public void canGetComponentsWithCustomClassLoader() {
        Container container = new XMLPicoContainer("org/jbehave/container/pico/components.xml", Thread
                .currentThread().getContextClassLoader());
        assertNotNull(container.getComponent(AComponent.class));
        assertNotNull(container.getComponent(AnotherComponent.class));
    }

    @Test(expected = InvalidContainerException.class)
    public void cannotGetComponentsWithInvalidClassLoader() throws MalformedURLException {
        new XMLPicoContainer("org/jbehave/container/pico/components.xml", new InvalidClassLoader());
    }

    @Test(expected = ComponentNotFoundException.class)
    public void cannotGetComponentWithNoneConfigured() {
        Container container = new XMLPicoContainer("org/jbehave/container/pico/no-components.xml");
        container.getComponent(AComponent.class);
    }

    @Test(expected = InvalidContainerException.class)
    public void cannotGetResourceWhenNotFound() {
        new XMLPicoContainer("inexistent-resource.xml");
    }

    class InvalidClassLoader extends URLClassLoader {
        public InvalidClassLoader() throws MalformedURLException {
            super(new URL[] {});
        }

        public InputStream getResourceAsStream(String resource) {
            return null;
        }
    }

}
