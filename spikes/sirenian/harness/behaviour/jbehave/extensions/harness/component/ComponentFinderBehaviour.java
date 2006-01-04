package jbehave.extensions.harness.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jbehave.core.minimock.UsingMiniMock;
import jbehave.extensions.harness.component.ComponentFilter;
import jbehave.extensions.harness.component.ComponentFinder;
import jbehave.extensions.harness.component.ComponentFinderException;


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

        ensureThat(componentList.size(), eq(2));
        ensureThat(componentList.contains(leftTwinButton));
        ensureThat(componentList.contains(rightTwinButton));
        
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
        
        ensureThat(component, eq(onlyButton));
        
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
        	ensureThat(anExceptionIsThrown());
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
        	ensureThat(anExceptionIsThrown());
        } catch (ComponentFinderException cfe) { }
    }
	
    
    private boolean anExceptionIsThrown() {
		return false;
	}
	
}
