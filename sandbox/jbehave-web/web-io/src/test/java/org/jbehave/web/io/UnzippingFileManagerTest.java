package org.jbehave.web.io;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class UnzippingFileManagerTest  {

	private static final String TMP = System.getProperty("java.io.tmpdir");
	private File upload;
	
	@Before
	public void setup() throws IOException{
		upload = new File(TMP, "upload");	
		upload.mkdirs();
	}
	
	@Test
	public void canListFilesThatAreNotDirectories() throws IOException {
		File dir1 = createDir("dir1");
		assertTrue(dir1.exists());
		File file1 = create("file1");
		File file2 = create("file2");	
		FileManager manager = new UnzippingFileManager(new FileUnzipper(), upload);
		assertEquals(asList(file1, file2), manager.list());
	}

	@Test
	public void canDeleteFilesAndDirectories() throws IOException {
		File dir1 = createDir("dir1");
		assertTrue(dir1.exists());
		File file1 = create("file1");
		File file2 = create("file2");	
		FileManager manager = new UnzippingFileManager(new FileUnzipper(), upload);
		assertEquals(asList(file1, file2), manager.list());
		manager.delete(asList(file1.getAbsolutePath()));
		assertEquals(asList(file2), manager.list());
		manager.delete(asList(dir1.getAbsolutePath()));
		assertFalse(dir1.exists());		
	}

	private File create(String path) throws IOException {
		File file = new File(upload, path);
		file.createNewFile();
		return file;
	}
	
	private File createDir(String path) throws IOException {
		File file = new File(upload, path);
		file.mkdirs();
		return file;
	}

}