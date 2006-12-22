package wizzards;
/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
import jbehave.plugin.eclipse.model.StoryLine;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;

import org.eclipse.jdt.core.IJavaElement;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.jdt.ui.wizards.NewClassWizardPage;

import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jdt.internal.ui.wizards.NewElementWizard;
import org.eclipse.jdt.internal.ui.wizards.NewWizardMessages;

public class NewClassWizard extends NewElementWizard {

	private NewClassWizardPage fPage;
	private final StoryLine storyLine;
	private final boolean usingMinimock;
	
	
	public NewClassWizard(StoryLine storyLine, boolean usingMinimock) {
		
		this.storyLine = storyLine;
		this.usingMinimock = usingMinimock;
		setDefaultPageImageDescriptor(JavaPluginImages.DESC_WIZBAN_NEWCLASS);
		setDialogSettings(JavaPlugin.getDefault().getDialogSettings());
		setWindowTitle(NewWizardMessages.NewClassCreationWizard_title);
		
		
	}
	
	
	/*
	 * @see Wizard#createPages
	 */	
	public void addPages() {
		super.addPages();
		if (fPage == null) {
			fPage= new NewClassWizardPage();
			fPage.init(getSelection());
			fPage.setMethodStubSelection(false, false, true, true);
			fPage.setTypeName(storyLine.asClassName(), true);
			if ("given".equalsIgnoreCase(storyLine.getKeyWord())){
				if (usingMinimock){
					fPage.setSuperClass("jbehave.core.story.domain.GivenUsingMiniMock",true);
				}
				else{
					fPage.addSuperInterface("jbehave.core.story.domain.Given");
				}
			} else
				if ("when".equalsIgnoreCase(storyLine.getKeyWord())){
					if (usingMinimock){
						fPage.setSuperClass("jbehave.core.story.domain.EventUsingMiniMock",true);
					}
					else{
						fPage.addSuperInterface("jbehave.core.story.domain.Event");
					}
				} else
					if ("then".equalsIgnoreCase(storyLine.getKeyWord())){
						if (usingMinimock){
							fPage.setSuperClass("jbehave.core.story.domain.OutcomeUsingMiniMock",true);
						}
						else{
							fPage.addSuperInterface("jbehave.core.story.domain.Outcome");
						}
					} 
			
		}
		addPage(fPage);
	}
	
	/*(non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.wizards.NewElementWizard#canRunForked()
	 */
	protected boolean canRunForked() {
		return !fPage.isEnclosingTypeSelected();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.wizards.NewElementWizard#finishPage(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {
		fPage.createType(monitor); // use the full progress monitor
	}
		
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		warnAboutTypeCommentDeprecation();
		boolean res= super.performFinish();
		if (res) {
			IResource resource= fPage.getModifiedResource();
			if (resource != null) {
				selectAndReveal(resource);
				openResource((IFile) resource);
			}	
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.wizards.NewElementWizard#getCreatedElement()
	 */
	public IJavaElement getCreatedElement() {
		return fPage.getCreatedType();
	}

}
