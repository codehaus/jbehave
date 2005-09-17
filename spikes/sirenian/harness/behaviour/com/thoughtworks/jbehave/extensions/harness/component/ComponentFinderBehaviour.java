package com.thoughtworks.jbehave.extensions.harness.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.thoughtworks.jbehave.core.Verify;
import com.thoughtworks.jbehave.core.minimock.UsingMiniMock;

public class ComponentFinderBehaviour extends UsingMiniMock {
    
    public void shouldFindMatchingComponentsFromFilter() {
        
        JFrame frame = new JFrame();
        JPanel centrePanel = new JPanel();
        JPanel southPanel = new JPanel();
        
        JButton leftTwinButton = new JButton("twin");
        JButton rightTwinButton = new JButton("twin");
        
        southPanel.add(leftTwinButton);
        southPanel.add(rightTwinButton);
        
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(centrePanel, BorderLayout.CENTER);
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
                
        ComponentFinder componentFinder = new ComponentFinder();
        
        Component[] components = componentFinder.findComponents(frame, new ComponentFilter() {
			public boolean matches(Component candidate) {
				return (candidate instanceof JButton 
						&& ((JButton)candidate).getText().equals("twin"));
			}        	
        });
        
        List componentList = Arrays.asList(components);

        Verify.that(componentList.size() == 2);
        Verify.that(componentList.contains(leftTwinButton));
        Verify.that(componentList.contains(rightTwinButton));
        
        verifyMocks();
    }
    
    public void shouldFindExactMatchingComponentFromFilter() throws ComponentFinderException {
        JFrame frame = new JFrame();
        JPanel centrePanel = new JPanel();
        JPanel southPanel = new JPanel();
        
        JButton onlyButton = new JButton("only");
        
        southPanel.add(onlyButton);
        
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(centrePanel, BorderLayout.CENTER);
        frame.getContentPane().add(southPanel, BorderLayout.SOUTH);
                
        ComponentFinder componentFinder = new ComponentFinder();
        
        Component component = componentFinder.findExactComponent(frame, new ComponentFilter() {
        	public boolean matches(Component candidate) {
        		return (candidate instanceof JButton
        				&& ((JButton)candidate).getText().equals("only"));
        	}
        });
        
        Verify.that(component == onlyButton);
        
        verifyMocks();
    }
    
    public void shouldThrowExceptionIfExactComponentSoughtAndNoComponentFound() {
        JFrame frame = new JFrame();
                               
        ComponentFinder componentFinder = new ComponentFinder();
        
        ComponentFilter filter = new ComponentFilter() {
			public boolean matches(Component candidate) {
				return false;
			}        	
        };
        
        try {
        	componentFinder.findExactComponent(frame, filter);
        	Verify.that("Should have thrown exception", false);
        } catch (ComponentFinderException cfe) { }
    }
    
    public void shouldThrowExceptionIfExactComponentSoughtAndMoreThanOneFound() {
    	JFrame frame = new JFrame();
    	frame.getContentPane().add(new JButton());
    	frame.getContentPane().add(new JButton());
    	
        ComponentFinder componentFinder = new ComponentFinder();
        
        ComponentFilter filter = new ComponentFilter() {
			public boolean matches(Component candidate) {
				return true; // will also match on root pane, content pane etc.
			}        	
        };
        
        try {
        	componentFinder.findExactComponent(frame, filter);
        	Verify.that("Should have thrown exception", false);
        } catch (ComponentFinderException cfe) { }
    }    
}
