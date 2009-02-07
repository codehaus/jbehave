package org.jbehave.web.waffle.controllers;

import java.io.File;
import java.util.List;

import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.web.io.FileManager;

public class FilesController extends MenuAwareController {

	private final FileManager manager;
	private List<File> files;

	public FilesController(Menu menu, FileManager manager) {
		super(menu);
		this.manager = manager;
	}

	@ActionMethod(asDefault = true)
	public void list() {
		this.files = manager.list();
	}

	public List<File> getFiles() {
		return files;
	}

}
