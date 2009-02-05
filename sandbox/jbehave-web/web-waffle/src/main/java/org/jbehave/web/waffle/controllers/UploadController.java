package org.jbehave.web.waffle.controllers;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.codehaus.waffle.action.annotation.ActionMethod;
import org.codehaus.waffle.action.annotation.PRG;
import org.codehaus.waffle.io.FileUploader;

public class UploadController {

	private static final String UPLOAD_DIR_FIELD = "uploadDirectory";
	private static final String DEFAULT_UPLOAD_DIR = "upload";
	private FileUnzipper unzipper;
	private FileUploader uploader;
	private Collection<String> errors;
	private String uploadedPath;

	public UploadController(FileUploader uploader) {
		this.uploader = uploader;
		this.unzipper = new FileUnzipper();
	}

	@ActionMethod(asDefault = true)
	@PRG(false)
	public void upload() {
		List<FileItem> files = uploader.getFiles();
		errors = uploader.getErrors();
		File uploadDirectory = uploadDirectory();
		if (files.size() > 0) {
			FileItem item = files.iterator().next();
			File file = writeToFile(uploadDirectory, item);
			this.uploadedPath = file.getAbsolutePath();
			unzip(file, uploadDirectory);
		}
	}

	private File uploadDirectory() {
		String uploadPath = DEFAULT_UPLOAD_DIR;
		for (FileItem item : uploader.getFormFields()) {
			if (UPLOAD_DIR_FIELD.equals(item.getFieldName())) {
				uploadPath = item.getString();
			}
		}
		File dir = new File(System.getProperty("java.io.tmpdir"), uploadPath);
		dir.mkdirs();
		return dir;
	}

	private File writeToFile(File uploadDirectory, FileItem item) {
		try {
			File file = new File(uploadDirectory, item.getName());
			item.write(file);
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void unzip(File file, File uploadDirectory) {
		unzipper.unzip(file, uploadDirectory);
	}

	public Collection<String> getErrors() {
		return errors;
	}

	public String getUploadedPath() {
		return uploadedPath;
	}

}
