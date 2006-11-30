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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.TextSelectionNavigationLocation;

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
				findAndOpenClass(storyLine.asClassName());
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	

	

	private void findAndOpenClass(String className) {
		try {
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
					EditorUtility.openInEditor((IType)types.get(0), true);
				}
			
		} catch (JavaModelException e) {
			throw new RuntimeException(e);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		
	}


	

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
