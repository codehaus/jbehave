package jbehave.plugin.eclipse.actions;

import java.util.ArrayList;
import java.util.List;

import jbehave.plugin.eclipse.editors.StoryEditor;
import jbehave.plugin.eclipse.model.StoryLine;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.TextSelectionNavigationLocation;

import wizzards.NewClassWizard;

public class NavigateToJavaSource implements IEditorActionDelegate {


	private StoryEditor editor;

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.editor=(StoryEditor)targetEditor;

	}




	public void run(IAction action) {
		IDocumentProvider dp = editor.getDocumentProvider();
		IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		TextSelectionNavigationLocation navloc = (TextSelectionNavigationLocation) editor.createNavigationLocation();
		TextSelection selection=(TextSelection) editor.getSelectionProvider().getSelection();
		int line=selection.getStartLine();
		try {
			String text=document.get(document.getLineOffset(line),document.getLineLength(line));
			jbehave.plugin.eclipse.model.StoryLine storyLine = StoryLine.parseLine(text);
			if (storyLine!=null){
				findAndOpenClass(storyLine);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}





	private void findAndOpenClass(StoryLine storyLine) {
		try {
			String className=storyLine.asClassName();
			final List types = new ArrayList();
		    SearchPattern pattern = SearchPattern.createPattern(className, IJavaSearchConstants.TYPE, IJavaSearchConstants.DECLARATIONS, SearchPattern.R_EXACT_MATCH);
		    IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
		    SearchRequestor requestor = new SearchRequestor(){
				@Override
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					types.add(match.getElement());
				}
		    };

		    SearchEngine searchEngine = new SearchEngine();
		    searchEngine.search(
		    		pattern,
		    		new SearchParticipant[] {SearchEngine.getDefaultSearchParticipant()},
		    		scope,
		    		requestor,
		    		null);
				if (types.size()==1){
                    openInEditor((IType) types.get(0));
                }
				if (types.size()==0){
                    bringUpNewClassWizard(storyLine);
                }
			    if (types.size()>1){
                    openInEditor(selectType(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),className));
                }

        } catch (JavaModelException e) {
			throw new RuntimeException(e);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		
	}

    public IType selectType(Shell parent, String filter) throws JavaModelException {
        SelectionDialog dialog= JavaUI.createTypeDialog(
            parent, new ProgressMonitorDialog(parent),
            SearchEngine.createWorkspaceScope(),
            IJavaElementSearchConstants.CONSIDER_ALL_TYPES, false,
            filter

       );
        dialog.setTitle("Choose Class");
        dialog.setMessage("");
        if (dialog.open() == IDialogConstants.CANCEL_ID)
            return null;

        Object[] types= dialog.getResult();
        if (types == null || types.length == 0)
            return null;
        return (IType)types[0];
    }

    private void openInEditor(IType type) throws JavaModelException, PartInitException {
        EditorUtility.openInEditor(type, true);
    }

    private void bringUpNewClassWizard( StoryLine storyLine) {
        Shell shell= PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        MessageDialog dialog=new MessageDialog(null,"Type "+storyLine.asClassName()+" not found",null,"Do you want to create it?",
                                                MessageDialog.QUESTION,
                                                new String[]{"Yes","Yes, using Minimock","No"},0);
        int ret=dialog.open();
        if ((ret==0)||(ret==1)){
            NewClassWizard wizard=new NewClassWizard(storyLine,ret==1);
            wizard.init(PlatformUI.getWorkbench(), null);
            WizardDialog wd=new WizardDialog(shell,wizard);

            wd.open();
        }
    }


    public void selectionChanged(IAction action, ISelection selection) {
        // TODO Auto-generated method stub

    }

}
