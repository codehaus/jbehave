package org.jbehave.container.spring;

import static java.text.MessageFormat.format;

import org.jbehave.container.ComponentNotFoundException;
import org.jbehave.container.Container;
import org.jbehave.container.InvalidContainerException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * <p>
 * Abstract implementation of Container which uses a Spring's BeanFactory as
 * delegate container.
 * </p>
 * 
 * @author Mauro Talevi
 */
public class SpringContainer implements Container {

    private BeanFactory factory;

    public SpringContainer(String resource) {
        this(resource, Thread.currentThread().getContextClassLoader());
    }

    public SpringContainer(String resource, ClassLoader classLoader) {
        this.factory = buildBeanFactory(resource, classLoader);
    }

    private BeanFactory buildBeanFactory(String resource, ClassLoader classLoader) {
        try {
            XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(new XmlBeanFactory(new ClassPathResource(
                    resource, classLoader)));
            reader.setBeanClassLoader(classLoader);
            reader.loadBeanDefinitions(resource);
            return (BeanFactory) reader.getBeanFactory();
        } catch (BeansException e) {
            String message = format("Failed to create container for resource {0}", resource);
            throw new InvalidContainerException(message);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> type) {
        try {
            return (T) factory.getBean(type.getName());
        } catch (BeansException e) {
            String message = format("No component registered in container of type {0}", type);
            throw new ComponentNotFoundException(message);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> type, Object key) {
        try {
            return (T) factory.getBean(key.toString());
        } catch (BeansException e) {
            String message = format("No component registered in container of key {0}", key);
            throw new ComponentNotFoundException(message);
        }
    }

}
