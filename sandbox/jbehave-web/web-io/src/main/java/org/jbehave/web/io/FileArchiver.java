package org.jbehave.web.io;

import java.io.File;
import java.util.List;

public interface FileArchiver {

	void archive(File archive, List<File> files);

	void unarchive(File archive, File outputDir);

	boolean isArchive(File file);

	File unarchivedDir(File file);	

}