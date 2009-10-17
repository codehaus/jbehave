/**
 * 
 */
package org.jbehave.web.io;

import java.io.File;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

/**
 * Manages data files, allowing the upload, list and delete. If a file is any
 * archive, the contents is unarchived to the directory with the corresponding
 * name af the archive (e.g. for archive path "/path/to/archive.zip", the output
 * directory path will be "/path/to/archive". The manager also allows to list
 * the content of a uploaded and unarchived file paths, e.g. the content of
 * "/path/to/archive".
 */
public interface FileManager {

	List<File> list();

	List<File> listContent(String path, boolean relativePaths);

	void delete(List<String> paths);

	List<File> upload(List<FileItem> fileItems, List<String> errors);

}