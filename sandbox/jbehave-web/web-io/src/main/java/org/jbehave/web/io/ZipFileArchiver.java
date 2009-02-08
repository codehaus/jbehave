package org.jbehave.web.io;

import static org.apache.commons.lang.StringUtils.removeEnd;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class ZipFileArchiver implements FileArchiver {

	private static final String ZIP = ".zip";

	public boolean isArchive(File file) {
		return file.getName().endsWith(ZIP);
	}

	public File unarchivedDir(File file) {
		return new File(removeEnd(file.getPath(), ZIP));
	}

	public void archive(File archive, List<File> files) {
		try {
			// Open archive stream
			FileOutputStream fileStream = new FileOutputStream(archive);
			ZipOutputStream zipStream = new ZipOutputStream(fileStream);

			for (File file : files) {
				if (!file.exists() || file.isDirectory()) {
					continue;
				}

				// Add archive entry
				ZipEntry entry = new ZipEntry(file.getName());
				zipStream.putNextEntry(entry);

				// Copy file to output
				copy(file, zipStream);
			}

			zipStream.close();
			fileStream.close();
		} catch (Exception e) {
			throw new FileArchiveFailedException(archive, files);
		}
	}

	private void copy(File file, ZipOutputStream out)
			throws FileNotFoundException, IOException {
		FileInputStream in = new FileInputStream(file);
		try {
			IOUtils.copy(in, out);
		} finally {
			in.close();
		}
	}

	public void unarchive(File archive, File outputDir) {
		try {
			ZipFile zipfile = new ZipFile(archive);
			for (Enumeration<?> e = zipfile.entries(); e.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) e.nextElement();
				unzipEntry(zipfile, entry, outputDir);
			}
		} catch (Exception e) {
			throw new FileUnarchiveFailedException(archive, outputDir);
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

		BufferedInputStream in = new BufferedInputStream(zipfile
				.getInputStream(entry));
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(outputFile));

		try {
			IOUtils.copy(in, out);
		} finally {
			out.close();
			in.close();
		}
	}

	private void createDir(File dir) throws IOException {
		if (!dir.mkdirs()) {
			throw new IOException("Failed to create dir " + dir);
		}
	}

	@SuppressWarnings("serial")
	public static final class FileArchiveFailedException extends
			RuntimeException {

		public FileArchiveFailedException(File archive, List<File> files) {
			super(files.toString() + File.separator + files.toString());
		}

	}

	@SuppressWarnings("serial")
	public static final class FileUnarchiveFailedException extends
			RuntimeException {

		public FileUnarchiveFailedException(File archive, File outputDir) {
			super(outputDir.toString() + File.separator + archive.toString());
		}

	}

}
