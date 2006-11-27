package jbehave.plugin.eclipse.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import jbehave.plugin.eclipse.editors.BehaviourEditor;

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
import org.eclipse.jdt.core.search.TypeNameRequestor;
import org.eclipse.jdt.internal.ui.javaeditor.EditorUtility;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INavigationLocation;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.TextSelectionNavigationLocation;

public class NavigateToJavaSource implements IEditorActionDelegate {

	List keyWords=Arrays.asList(new String[]{"title:","scenario:","when","then","given"});
	private BehaviourEditor editor;

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.editor=(BehaviourEditor)targetEditor;
		
	}
	
	 public static String capitalize(String str) {
	        int strLen;
	        if (str == null || (strLen = str.length()) == 0) {
	            return str;
	        }
	        return new StringBuffer(strLen)
	            .append(Character.toTitleCase(str.charAt(0)))
	            .append(str.substring(1))
	            .toString();
	    }


	public void run(IAction action) {
		IDocumentProvider dp = editor.getDocumentProvider();
		IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		TextSelectionNavigationLocation navloc = (TextSelectionNavigationLocation) editor.createNavigationLocation();
		TextSelection selection=(TextSelection) editor.getSelectionProvider().getSelection();
		int line=selection.getStartLine();
		try {
			String text=document.get(document.getLineOffset(line),document.getLineLength(line));
			processLine(text);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void processLine(String line) {
		line=line.replaceAll("\n", "");
		line=line.replaceAll("\r", "");
		List words=new ArrayList(Arrays.asList(line.split(" ")));
		if (words.size()<2)
			return;
		
		if (((String)words.get(0)).equalsIgnoreCase("and")){
			words.remove(0);
			if (words.size()<2)
				return;
		}
		
		if (keyWords.contains(((String)words.get(0)).toLowerCase())){	
			words.remove(0);
			String className = renderClassName(words);
			findAndOpenClass(className);
		}
		
	}

	private String renderClassName(List words) {
		String className="";
		for (Iterator iter = words.iterator(); iter.hasNext();) {
			String word = (String) iter.next();
			className+=capitalize(word);				
		}
		System.out.println("Class: '"+className+"'");
		return className;
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
