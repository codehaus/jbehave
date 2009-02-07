package org.jbehave.web.waffle.controllers;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.action.annotation.PRG;
import org.codehaus.waffle.io.FileUploader;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.jbehave.web.io.FileManager;

public class UploadController extends MenuAwareController {

	private final FileUploader uploader;
	private final FileManager manager;
	private List<String> errors = new ArrayList<String>();
	private List<String> uploadedPaths = new ArrayList<String>();
	private int filesToUpload = 1;

	public UploadController(Menu menu, FileUploader uploader, FileManager manager) {
		super(menu);
		this.uploader = uploader;
		this.manager = manager;
	}

	@ActionMethod(asDefault = true)
	@PRG(false)
	public void upload() {
		errors.clear();
		uploadedPaths.clear();
		List<FileItem> files = uploader.getFiles();
		errors.addAll(uploader.getErrors());
		List<FileItem> formFields = uploader.getFormFields();
		File uploadDirectory = manager.uploadDirectory(formFields);
		uploadedPaths.addAll(manager.write(files, uploadDirectory, true,
				errors));
	}

	public Collection<String> getErrors() {
		return errors;
	}

	public List<String> getUploadedPaths() {
		return uploadedPaths;
	}

	public int getFilesToUpload() {
		return filesToUpload;
	}

	public void setFilesToUpload(int filesToUpload) {
		this.filesToUpload = filesToUpload;
	}

}
