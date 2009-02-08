package org.jbehave.web.io;

import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;

import org.jbehave.web.io.ZipFileArchiver.FileArchiveFailedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZipFileArchiverTest {

	private static final String TMP = System.getProperty("java.io.tmpdir");
	private File upload;
	private File dir1;
	private File file1;
	private File file2;
	private File zip;

	@Before
	public void setup() throws IOException {
		upload = new File(TMP, "upload");
		upload.mkdirs();
		dir1 = createDir("dir1");
		file1 = create("file1");
		file2 = create("file2");
		zip = create("dir1.zip");

	}

	@After
	public void tearDown() throws IOException {
		file1.delete();
		file2.delete();
		dir1.delete();
		zip.delete();
	}

	@Test
	public void canArchiveFilesAndDirectories() throws IOException {
		FileArchiver archiver = new ZipFileArchiver();
		archiver.archive(zip, asList(dir1, file1, file2));
	}
	
	@Test
	public void canArchiveInexistingFilesAndDirectories() throws IOException {
		FileArchiver archiver = new ZipFileArchiver();
		file1.delete();
		archiver.archive(zip, asList(dir1, file1, file2));
	}
	
	@Test(expected=FileArchiveFailedException.class)
	public void cannotArchiveNullEntries() throws Exception {
		FileArchiver archiver = new ZipFileArchiver();
		archiver.archive(zip, asList((File)null));
	}

	private File create(String path) throws IOException {
		File file = new File(upload, path);
		file.createNewFile();
		return file;
	}

	private File createDir(String path) throws IOException {
		File dir = new File(upload, path);
		dir.mkdirs();
		File child = new File(dir, "child1");
		child.createNewFile();
		return dir;
	}

}