package com.thoughtworks.jbehave.extensions.harness.component;

import java.awt.AWTEvent;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.thoughtworks.jbehave.core.Verify;

public class DefaultWindowWrapperBehaviour {
	
	public void shouldClickAButtonOnAWindow() throws Exception {
		
		JFrame frame = new JFrame();
		frame.setName("a.window");
		
		JButton button = new JButton("Press Me!");
		button.setName("a.button");
		final ObjectHolder o = new ObjectHolder();
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				o.object = event;
			}
		});
		
		frame.getContentPane().add(button);
		frame.pack();
		
		DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
		
		frame.setVisible(true);
		wrapper.clickButton("a.button");		
		frame.dispose();
		
		Verify.that(o.object != null);		
	}
//	
//	public void shouldEnterTextIntoTextComponents() throws TimeoutException {
//		
//		JFrame frame = new JFrame();
//		frame.setName("a.window");
//		
//		JTextComponent textField = new JTextField();
//		textField.setName("a.textfield");
//		
//		JTextComponent textArea = new JTextArea();
//		textArea.setName("b.textarea");
//		
//		frame.getContentPane().add(textField);
//		frame.pack();
//		
//		DefaultWindowWrapper wrapper = new DefaultWindowWrapper("a.window");
//		
//		frame.setVisible(true);
//		wrapper.enterText("a.textfield", "Text1");
//		wrapper.enterText("b.textarea", "Text2");
//		
//		Verify.that("Text1".equals(textField.getText()));
//		Verify.that("Text2".equals(textArea.getText()));
//	}
//	
//	
//	
	private class ObjectHolder {
		public Object object;
	}
}
