package org.jbehave.web.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

// Taken from http://piotrga.wordpress.com/2008/05/07/how-to-unzip-archive-in-java/
public class FileUnzipper {

	public void unzip(File archive, File outputDir) {
		try {
			ZipFile zipfile = new ZipFile(archive);
			for (Enumeration<?> e = zipfile.entries(); e.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) e.nextElement();
				unzipEntry(zipfile, entry, outputDir);
			}
		} catch (Exception e) {
			throw new FileUnzipFailedException(archive, outputDir);
		}
	}

	private void unzipEntry(ZipFile zipfile, ZipEntry entry, File outputDir)
			throws IOException {

		if (entry.isDirectory()) {
			createDir(new File(outputDir, entry.getName()));
			return;
		}

		File outputFile = new File(outputDir, entry.getName());
		if (!outputFile.getParentFile().exists()) {
			createDir(outputFile.getParentFile());
		}

		BufferedInputStream inputStream = new BufferedInputStream(zipfile
				.getInputStream(entry));
		BufferedOutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(outputFile));

		try {
			IOUtils.copy(inputStream, outputStream);
		} finally {
			outputStream.close();
			inputStream.close();
		}
	}

	private void createDir(File dir) throws IOException {
		if (!dir.mkdirs()) {
			throw new IOException("Failed to create dir " + dir);
		}
	}
	
	@SuppressWarnings("serial")
	public static final class FileUnzipFailedException extends RuntimeException {

		public FileUnzipFailedException(File archive, File outputDir) {
			super(outputDir.toString()+File.separator+archive.toString());
		}
		
	}

}
