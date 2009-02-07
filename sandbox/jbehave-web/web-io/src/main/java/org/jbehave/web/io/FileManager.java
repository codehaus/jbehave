/**
 * 
 */
package org.jbehave.web.io;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.jbehave.web.io.FileUnzipper.FileUnzipFailedException;

public class FileManager {
	private static final String UPLOAD_DIR_FIELD = "uploadDirectory";
	private static final String DEFAULT_UPLOAD_DIR = "upload";

	public FileUnzipper unzipper;

	public FileManager(FileUnzipper unzipper) {
		this.unzipper = unzipper;
	}

	public File uploadDirectory(List<FileItem> formFields) {
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

	public List<String> write(List<FileItem> files, File directory,
			boolean unzip, List<String> errors) {
		List<String> paths = new ArrayList<String>();
		for (FileItem item : files) {
			try {
				File file = writeToFile(directory, item);
				paths.add(file.getAbsolutePath());
				if (unzip) {
					try {
						unzipper.unzip(file, directory);
					} catch (FileUnzipFailedException e) {
						errors.add(e.getMessage());
					}
				}
			} catch (FileManager.FileItemNameMissingException e) {
				// ignore and carry on
			} catch (FileManager.FileWriteFailedException e) {
				errors.add(e.getMessage());
			}
		}
		return paths;
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
	public static final class FileItemNameMissingException extends
			RuntimeException {

		public FileItemNameMissingException(FileItem file) {
			super(file.toString());
		}

	}

	@SuppressWarnings("serial")
	public static final class FileWriteFailedException extends
			RuntimeException {

		public FileWriteFailedException(File file, Throwable cause) {
			super(file.toString(), cause);
		}

	}
}