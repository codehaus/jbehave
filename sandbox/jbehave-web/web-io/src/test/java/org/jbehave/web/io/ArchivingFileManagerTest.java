package org.jbehave.web.io;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class ArchivingFileManagerTest {

	private Mockery mockery = new Mockery();
	private static final FileArchiver archiver = new ZipFileArchiver();
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
		archiver.archive(zip, asList(file1, file2));

	}
	
	@After
	public void tearDown() throws IOException {
		file1.delete();
		file2.delete();
		dir1.delete();
		zip.delete();
	}

	@Test
	public void canListFilesThatAreNotDirectories() throws IOException {
		FileManager manager = new ArchivingFileManager(archiver, upload);
		assertEquals(asList(zip, file1, file2), manager.list());
	}

	@Test
	public void canDeleteFilesAndDirectories() throws IOException {
		FileManager manager = new ArchivingFileManager(archiver, upload);
		assertEquals(asList(zip, file1, file2), manager.list());
		manager.delete(asList(file1.getAbsolutePath()));
		assertEquals(asList(zip, file2), manager.list());
		manager.delete(asList(zip.getAbsolutePath()));
		assertEquals(asList(file2), manager.list());
	}

	@Test
	public void canWriteFileItems() throws Exception {
		FileManager manager = new ArchivingFileManager(archiver, upload);
		List<String> errors = new ArrayList<String>();
		final FileItem file2FileItem = mockery.mock(FileItem.class, "file2");
		final FileItem zipFileItem = mockery.mock(FileItem.class, "zip");
		mockery.checking(new Expectations() {
			{
				allowing(zipFileItem).getName();
				will(returnValue(zip.getName()));
				one(zipFileItem).write(zip);
				allowing(file2FileItem).getName();
				will(returnValue(file2.getName()));
				one(file2FileItem).write(file2);
			}
		});
		// ensure files do not exists
		file2.delete(); 
		zip.delete(); 
		manager.write(asList(file2FileItem, zipFileItem), errors);
	}
	

	@Test
	public void canIgnoreWritingFileItemsWithBlankNames() throws Exception {
		FileManager manager = new ArchivingFileManager(archiver, upload);
		List<String> errors = new ArrayList<String>();
		final FileItem file2FileItem = mockery.mock(FileItem.class, "file2");
		final FileItem zipFileItem = mockery.mock(FileItem.class, "zip");
		mockery.checking(new Expectations() {
			{
				allowing(zipFileItem).getName();
				will(returnValue(""));
				allowing(file2FileItem).getName();
				will(returnValue(""));
			}
		});
		manager.write(asList(file2FileItem, zipFileItem), errors);
	}
	
	@Test
	public void cannotOverWriteExistingFileItems() throws Exception {
		FileManager manager = new ArchivingFileManager(archiver, upload);
		List<String> errors = new ArrayList<String>();
		final FileItem file2FileItem = mockery.mock(FileItem.class, "file2");
		final FileItem zipFileItem = mockery.mock(FileItem.class, "zip");
		mockery.checking(new Expectations() {
			{
				allowing(zipFileItem).getName();
				will(returnValue(zip.getName()));
				allowing(file2FileItem).getName();
				will(returnValue(file2.getName()));
			}
		});
		manager.write(asList(file2FileItem, zipFileItem), errors);
		assertEquals(2, errors.size());
	}

	@Test
	public void cannotWriteFileItemsThatFail() throws Exception {
		FileManager manager = new ArchivingFileManager(archiver, upload);
		List<String> errors = new ArrayList<String>();
		final FileItem file2FileItem = mockery.mock(FileItem.class, "file2");
		final FileItem zipFileItem = mockery.mock(FileItem.class, "zip");
		mockery.checking(new Expectations() {
			{
				allowing(zipFileItem).getName();
				will(returnValue(zip.getName()));
				one(zipFileItem).write(zip);
				will(throwException(new IOException("zip write failed")));
				allowing(file2FileItem).getName();
				will(returnValue(file2.getName()));
				one(file2FileItem).write(file2);
				will(throwException(new IOException("file2 write failed")));
			}
		});
		// ensure files do not exists
		file2.delete(); 
		zip.delete(); 
		manager.write(asList(file2FileItem, zipFileItem), errors);
		assertEquals(2, errors.size());
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