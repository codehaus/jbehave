package org.jbehave.web.waffle.controllers;

import static org.apache.commons.lang.StringUtils.isBlank;

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
import org.jbehave.web.io.FileUnzipper;
import org.jbehave.web.io.FileUnzipper.FileUnzipFailedException;

public class UploadController extends MenuAwareController {

	private static final String UPLOAD_DIR_FIELD = "uploadDirectory";
	private static final String DEFAULT_UPLOAD_DIR = "upload";
	private FileUnzipper unzipper;
	private FileUploader uploader;
	private List<String> errors = new ArrayList<String>();
	private List<String> uploadedPaths = new ArrayList<String>();

	public UploadController(Menu menu, FileUploader uploader) {
		super(menu);
		this.uploader = uploader;
		this.unzipper = new FileUnzipper();
	}

	@ActionMethod(asDefault = true)
	@PRG(false)
	public void upload() {
		errors.clear();
		uploadedPaths.clear();
		List<FileItem> files = uploader.getFiles();
		errors.addAll(uploader.getErrors());
		List<FileItem> formFields = uploader.getFormFields();
		File uploadDirectory = uploadDirectory(formFields);
		uploadedPaths.addAll(upload(files, uploadDirectory, true, errors));
	}
	
	private File uploadDirectory(List<FileItem> formFields) {
		String uploadPath = DEFAULT_UPLOAD_DIR;
		for (FileItem item : formFields) {
			if (UPLOAD_DIR_FIELD.equals(item.getFieldName())) {
				uploadPath = item.getString();
			}
		}
		File dir = new File(System.getProperty("java.io.tmpdir"), uploadPath);
		dir.mkdirs();
		return dir;
	}

	private List<String> upload(List<FileItem> files, File uploadDirectory,
			boolean unzip, List<String> errors) {
		List<String> uploadedPaths = new ArrayList<String>();
		for (FileItem item : files) {
			try {
				File file = writeToFile(uploadDirectory, item);
				uploadedPaths.add(file.getAbsolutePath());
				if (unzip) {
					try {
						unzipper.unzip(file, uploadDirectory);
					} catch (FileUnzipFailedException e) {
						errors.add(e.getMessage());
					}
				}
			} catch (FileItemNameMissingException e) {
				// ignore and carry on
			} catch (FileWriteFailedException e) {
				errors.add(e.getMessage());
			}
		}
		return uploadedPaths;
	}

	private File writeToFile(File uploadDirectory, FileItem item) {
		if (isBlank(item.getName())) {
			throw new FileItemNameMissingException(item);
		}
		File file = new File(uploadDirectory, item.getName());
		try {
			item.write(file);
		} catch (Exception e) {
			throw new FileWriteFailedException(file, e);
		}
		return file;
	}

	@SuppressWarnings("serial")
	private static final class FileItemNameMissingException extends
			RuntimeException {

		public FileItemNameMissingException(FileItem file) {
			super(file.toString());
		}

	}

	@SuppressWarnings("serial")
	private static final class FileWriteFailedException extends
			RuntimeException {

		public FileWriteFailedException(File file, Throwable cause) {
			super(file.toString(), cause);
		}

	}

	public Collection<String> getErrors() {
		return errors;
	}

	public List<String> getUploadedPaths() {
		return uploadedPaths;
	}

}
