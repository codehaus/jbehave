package jbehave.plugin.eclipse.editors;

import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class StoryEditor extends TextEditor {

	private StoryContentOutlinePage fOutlinePage;

	public StoryEditor() {
		super();
		
	}
	public void dispose() {
	
		super.dispose();
	}
	
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			if (fOutlinePage == null) {
				fOutlinePage= new StoryContentOutlinePage(getDocumentProvider(), this);
				if (getEditorInput() != null)
					fOutlinePage.setInput(getEditorInput());
			}
			return fOutlinePage;
		}
		
		
		return super.getAdapter(required);
	}

}
