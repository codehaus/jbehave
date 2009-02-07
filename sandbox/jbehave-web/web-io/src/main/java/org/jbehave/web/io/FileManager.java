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
	
	private static final String UPLOAD_PATH = System.getProperty("java.io.tmpdir")+File.separator+"upload";

	private final FileUnzipper unzipper;
	private File uploadDir;

	public FileManager(FileUnzipper unzipper) {
		this.unzipper = unzipper;
	}

	public File uploadDirectory() {
		if (uploadDir == null) {
			uploadDir = new File(UPLOAD_PATH);
			uploadDir.mkdirs();
		}
		return uploadDir;
	}

	public List<String> write(List<FileItem> fileItems, File directory,
			List<String> errors) {
		List<String> paths = new ArrayList<String>();
		for (FileItem item : fileItems) {
			try {
				File file = writeToFile(directory, item);
				paths.add(file.getAbsolutePath());
				if ( isZip(file) ) {
					try {
						unzipper.unzip(file, directory);
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
		return paths;
	}

	private boolean isZip(File file) {
		return file.getName().endsWith(".zip");
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
	public static final class FileWriteFailedException extends RuntimeException {

		public FileWriteFailedException(File file, Throwable cause) {
			super(file.toString(), cause);
		}

	}
}