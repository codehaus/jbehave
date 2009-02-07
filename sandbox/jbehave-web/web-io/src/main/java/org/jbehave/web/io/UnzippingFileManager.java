/**
 * 
 */
package org.jbehave.web.io;

import static java.util.Arrays.asList;
import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.removeEnd;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.jbehave.web.io.FileUnzipper.FileUnzipFailedException;

public class UnzippingFileManager implements FileManager {

	private static final String ZIP = ".zip";
	private static final String UPLOAD_PATH = System
			.getProperty("java.io.tmpdir")
			+ File.separator + "upload";

	private final FileUnzipper unzipper;
	private File uploadDir;

	public UnzippingFileManager(FileUnzipper unzipper) {
		this.unzipper = unzipper;
	}

	public File uploadDirectory() {
		if (uploadDir == null) {
			uploadDir = new File(UPLOAD_PATH);
			uploadDir.mkdirs();
		}
		return uploadDir;
	}

	public List<File> list() {
		return asList(uploadDirectory().listFiles(new FileFilter(){
			public boolean accept(File file) {
				return !file.isDirectory();
			}			
		}));
	}

	public void delete(List<String> paths) {
		for (String path : paths) {
			deleteFile(new File(path));
		}
	}

	private boolean deleteFile(File file) {
		if (file.isDirectory()) {
			// recursively delete children
			for (String child : file.list()) {
				if (!deleteFile(new File(file, child))) {
					return false;
				}
			}
		}
		if ( isZip(file) ){
			deleteFile(withoutZip(file));
		}
		return file.delete();
	}

	private File withoutZip(File file) {
		return new File(removeEnd(file.getPath(), ZIP));
	}

	public List<File> write(List<FileItem> fileItems, List<String> errors) {
		List<File> files = new ArrayList<File>();
		File directory = uploadDirectory();
		for (FileItem item : fileItems) {
			try {
				File file = writeItemToFile(directory, item);
				files.add(file);
				if (isZip(file)) {
					try {
						unzipper.unzip(file, directory);
					} catch (FileUnzipFailedException e) {
						errors.add(e.getMessage());
					}
				}
			} catch (FileItemNameMissingException e) {
				// ignore and carry on
			} catch (FileAlreadyExistsException e) {
				errors.add(e.getMessage());
			} catch (FileWriteFailedException e) {
				errors.add(e.getMessage());
			}
		}
		return files;
	}

	private boolean isZip(File file) {
		return file.getName().endsWith(ZIP);
	}

	private File writeItemToFile(File directory, FileItem item) {
		if (isBlank(item.getName())) {
			throw new FileItemNameMissingException(item);
		}
		File file = new File(directory, item.getName());
		if (file.exists()) {
			throw new FileAlreadyExistsException(file);
		}
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
	public static final class FileAlreadyExistsException extends
			RuntimeException {

		public FileAlreadyExistsException(File file) {
			super(file.toString() + " already exists");
		}

	}

	@SuppressWarnings("serial")
	public static final class FileWriteFailedException extends RuntimeException {

		public FileWriteFailedException(File file, Throwable cause) {
			super(file.toString() + " write failed", cause);
		}

	}

}