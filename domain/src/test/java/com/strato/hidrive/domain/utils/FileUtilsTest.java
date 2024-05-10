package com.strato.hidrive.domain.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by y.zozulia on 29.09.2015.
 */
public class FileUtilsTest {
	private static final String REMOTE_FILE_PATH_SEPARATOR = "/";

	@Test
	public void testCorrectPathDoNotAddSlashToEndOfPathWhichContainsSlash() {
		String correctPath = "path/";
		String resultPath = FileUtils.correctPath(correctPath);
		assertEquals(correctPath, resultPath);
	}

	@Test
	public void testCorrectPathAddSlashToEndOfPathWhichDoesntContainsSlash() {
		String incorrectPath = "path";
		String resultPath = FileUtils.correctPath(incorrectPath);
		String correctPath = incorrectPath + "/";
		assertEquals(correctPath, resultPath);
	}

	@Test
	public void testDeleteDirReturnFalseWhenDirectoryIsNull() {
		File directory = null;
		assertFalse(FileUtils.deleteDir(directory));
	}

	@Test
	public void testDeleteDirDeleteDirectoryWithChildrenAndReturnTrue() throws IOException {
		File rootDirectory = new File("rootDirectory");
		File directory1 = new File(rootDirectory, "directory1");
		File file1 = new File(directory1, "file1");
		File file2 = new File(rootDirectory, "file2");

		rootDirectory.mkdir();
		directory1.mkdir();
		file1.createNewFile();
		file2.createNewFile();

		assertTrue(rootDirectory.exists());
		assertTrue(directory1.exists());
		assertTrue(file1.exists());
		assertTrue(file2.exists());

		assertTrue(FileUtils.deleteDir(rootDirectory));

		assertFalse(rootDirectory.exists());
		assertFalse(directory1.exists());
		assertFalse(file1.exists());
		assertFalse(file2.exists());

		file1.delete();
		file2.delete();
		directory1.delete();
		rootDirectory.delete();
	}

	@Test
	public void testGetDirectorySizeReturnsZeroWhenDirectoryIsNotExist() {
		File directory = createMockDirectory(false, null);
		assertEquals(0, FileUtils.getDirectorySize(directory));
	}

	@Test
	public void testGetDirectorySizeReturnsZeroWhenDirectoryIsFile() {
		File file = createMockFile(0);
		assertEquals(0, FileUtils.getDirectorySize(file));
	}

	@Test
	public void testGetDirectorySizeReturnZeroWhenDirectoryEmpty() {
		File directory = createMockDirectory(null);
		assertEquals(0, FileUtils.getDirectorySize(directory));
	}

	@Test
	public void testGetDirectorySizeReturnCorrectSizeWhenDirectoryIsNotEmpty() {
		File file1 = createMockFile(10);
		File file2 = createMockFile(20);
		File file3 = createMockFile(30);
		File file4 = createMockFile(40);

		File directory1 = createMockDirectory(new File[]{file1});
		File directory2 = createMockDirectory(new File[]{file2});

		File testDirectory = createMockDirectory(new File[]{directory1, directory2, file3, file4});

		assertEquals(100, FileUtils.getDirectorySize(testDirectory));
	}

	@Test
	public void testGetFileListSizeReturnZeroIfListEmpty() {
		assertEquals(0, FileUtils.getFileListSize(new ArrayList<File>()));
	}

	@Test
	public void testGetFileListSizeReturnSizeOnlyForFiles() {
		List<File> fileList = Arrays.asList(
				createMockFile(10),
				createMockFile(20),
				createMockFile(30),
				createMockDirectory(null),
				createMockDirectory(new File[]{createMockFile(40)})
		);

		assertEquals(60, FileUtils.getFileListSize(fileList));
	}

	@Test
	public void testDeleteFileOrDirDoNotCallDeleteWhenFileOrDirectoryNotExist() throws IOException {
		File notExistedFile = mock(File.class);
		when(notExistedFile.exists()).thenReturn(false);

		FileUtils.deleteFileOrDir(notExistedFile);
		verify(notExistedFile, never()).delete();
	}

	@Test
	public void testDeleteFileOrDirDeleteFile() throws IOException {
		File file = mock(File.class);
		when(file.exists()).thenReturn(true);
		when(file.isDirectory()).thenReturn(false);

		FileUtils.deleteFileOrDir(file);
		verify(file, times(1)).delete();
	}

	@Test
	public void testDeleteFileOrDirDeleteDirectoryWithChildren() throws IOException {
		File rootDirectory = new File("rootDirectory");
		File directory1 = new File(rootDirectory, "directory1");
		File file1 = new File(directory1, "file1");
		File file2 = new File(rootDirectory, "file2");

		rootDirectory.mkdir();
		directory1.mkdir();
		file1.createNewFile();
		file2.createNewFile();

		assertTrue(rootDirectory.exists());
		assertTrue(directory1.exists());
		assertTrue(file1.exists());
		assertTrue(file2.exists());

		FileUtils.deleteFileOrDir(rootDirectory);

		assertFalse(rootDirectory.exists());
		assertFalse(directory1.exists());
		assertFalse(file1.exists());
		assertFalse(file2.exists());

		file1.delete();
		file2.delete();
		directory1.delete();
		rootDirectory.delete();
	}

	@Test
	public void testDeleteFileWithPath() throws IOException {
		String fileName = "fileName";
		File file = new File(fileName);
		file.createNewFile();

		FileUtils.deleteFile(fileName);

		assertFalse(file.exists());

		file.delete();
	}

	@Test
	public void testDeleteFileCallDeleteMethodWhenFileExist() {
		File file = createMockFile(true);
		FileUtils.deleteFile(file);

		verify(file, times(1)).delete();
	}

	@Test
	public void testDeleteFileDoNotCallDeleteMethodWhenFileIsNotExist() {
		File file = createMockFile(false);
		FileUtils.deleteFile(file);

		verify(file, never()).delete();
	}

	@Test
	public void testGetFileOrDirSizeReturnSizeOfFile() {
		File file = createMockFile(10);

		assertEquals(10, FileUtils.getFileOrDirSize(file));
	}

	@Test
	public void testGetFileOrDirSizeReturnSizeOfDirectory() {
		File file1 = createMockFile(10);
		File file2 = createMockFile(20);
		File directory = createMockDirectory(new File[]{file1, file2});

		assertEquals(30, FileUtils.getFileOrDirSize(directory));
	}

	@Test
	public void testAddAllDirectoryFiles() {
		File file1 = createMockFileWithName("file1");
		File file2 = createMockFileWithName("file2");
		File file3 = createMockFileWithName("file3");

		File directory1 = createMockDirectoryWithName("directory1", new File[]{file1});
		File directory2 = createMockDirectoryWithName("directory2", new File[]{file2});

		File rootDirectory = createMockDirectoryWithName("rootDirectory", new File[]{directory1, directory2, file3});

		List<File> files = new ArrayList<>();
		FileUtils.addAllDirectoryFiles(rootDirectory, files);

		assertEquals(6, files.size());
		assertTrue(containFileWithName(files, "file1"));
		assertTrue(containFileWithName(files, "file2"));
		assertTrue(containFileWithName(files, "file3"));
		assertTrue(containFileWithName(files, "directory1"));
		assertTrue(containFileWithName(files, "directory2"));
		assertTrue(containFileWithName(files, "rootDirectory"));
	}

	private boolean containFileWithName(List<File> files, String name) {
		for (File file : files) {
			if (file.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void copyBetweenStreamsSuccessfullyAndReturnTrue() throws IOException {
		byte[] dataToSave = {2, 8, 7, 3, 6, 2, 3, 7, 6, 5, 9, 3, 4, 5};

		File fileToRead = new File("fileToRead");
		File fileToWrite = new File("fileToWrite");

		boolean ftr = fileToRead.createNewFile();
		boolean ftw = fileToWrite.createNewFile();

		assertTrue("Needed files haven't been created.", ftr && ftw);

		try (FileOutputStream out = new FileOutputStream(fileToRead);
			 FileInputStream in = new FileInputStream(fileToRead);
			 FileOutputStream destinationStream = new FileOutputStream(fileToWrite);
			 FileInputStream readFromCopiedFile = new FileInputStream(fileToWrite)) {
			out.write(dataToSave);
			long copyResult = FileUtils.copyBetweenStreams(in, destinationStream);
			assertTrue("Function result must be not null", copyResult > 0);
			byte[] readData = new byte[readFromCopiedFile.available()];
			readFromCopiedFile.read(readData, 0, readData.length);
			assertTrue("Byte arrays must be equal", isByteArraysEqual(dataToSave, readData));
		}

		fileToRead.delete();
		fileToWrite.delete();
	}

	private boolean isByteArraysEqual(byte[] byteArray1, byte[] byteArray2) {
		if (byteArray1.length == byteArray2.length) {
			for (int i = 0; i < byteArray1.length; i++) {
				if (byteArray1[i] != byteArray2[i]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Test
	public void testIsFileNameHasExtension() {
		assertFalse(FileUtils.isFileNameHasExtension(""));
		assertFalse(FileUtils.isFileNameHasExtension("name"));
		assertFalse(FileUtils.isFileNameHasExtension("name."));
		assertTrue(FileUtils.isFileNameHasExtension("name.mp4"));
		assertTrue(FileUtils.isFileNameHasExtension("name.mp3.mp4"));
		assertFalse(FileUtils.isFileNameHasExtension(".mp4"));
	}

	@Test
	public void testMakeNonConflictingFileName() {
		assertEquals("(1)", FileUtils.makeNonConflictingFileName("", 1));
		assertEquals("name(1)", FileUtils.makeNonConflictingFileName("name", 1));
		assertEquals("name.(1)", FileUtils.makeNonConflictingFileName("name.", 1));
		assertEquals("name(1).mp4", FileUtils.makeNonConflictingFileName("name.mp4", 1));
		assertEquals("name.mp3(1).mp4", FileUtils.makeNonConflictingFileName("name.mp3.mp4", 1));
		assertEquals(".mp4(1)", FileUtils.makeNonConflictingFileName(".mp4", 1));
	}

	@Test
	public void testMakeFileNameWithModifySuffix() {
		assertEquals(FileUtils.MODIFY_SUFFIX, FileUtils.makeFileNameWithModifySuffix(""));
		assertEquals("name" + FileUtils.MODIFY_SUFFIX, FileUtils.makeFileNameWithModifySuffix("name"));
		assertEquals("name." + FileUtils.MODIFY_SUFFIX, FileUtils.makeFileNameWithModifySuffix("name."));
		assertEquals("name" + FileUtils.MODIFY_SUFFIX + ".mp4", FileUtils.makeFileNameWithModifySuffix("name.mp4"));
		assertEquals("name.mp3" + FileUtils.MODIFY_SUFFIX + ".mp4", FileUtils.makeFileNameWithModifySuffix("name.mp3.mp4"));
		assertEquals(".mp4" + FileUtils.MODIFY_SUFFIX, FileUtils.makeFileNameWithModifySuffix(".mp4"));
		assertEquals(FileUtils.MODIFY_SUFFIX + FileUtils.MODIFY_SUFFIX, FileUtils.makeFileNameWithModifySuffix(FileUtils.MODIFY_SUFFIX));
	}

	@Test
	public void testExtractFileNameWithoutExtension() {
		assertEquals("", FileUtils.extractFileNameWithoutExtension(""));
		assertEquals("name", FileUtils.extractFileNameWithoutExtension("name"));
		assertEquals("name.", FileUtils.extractFileNameWithoutExtension("name."));
		assertEquals("name", FileUtils.extractFileNameWithoutExtension("name.mp4"));
		assertEquals("name.mp3", FileUtils.extractFileNameWithoutExtension("name.mp3.mp4"));
		assertEquals(".mp4", FileUtils.extractFileNameWithoutExtension(".mp4"));

		assertEquals("", FileUtils.extractFileNameWithoutExtension("/"));
		assertEquals("name", FileUtils.extractFileNameWithoutExtension("/name"));
		assertEquals("name.", FileUtils.extractFileNameWithoutExtension("/name."));
		assertEquals("name", FileUtils.extractFileNameWithoutExtension("/name.mp4"));
		assertEquals("name.mp3", FileUtils.extractFileNameWithoutExtension("/name.mp3.mp4"));
		assertEquals(".mp4", FileUtils.extractFileNameWithoutExtension("/.mp4"));
	}

	@Test
	public void testExtractFileName() {
		assertEquals("", FileUtils.extractFileName(null));

		assertEquals("", FileUtils.extractFileName(""));
		assertEquals("name", FileUtils.extractFileName("name"));
		assertEquals("name.", FileUtils.extractFileName("name."));
		assertEquals("name.mp4", FileUtils.extractFileName("name.mp4"));
		assertEquals("name.mp3.mp4", FileUtils.extractFileName("name.mp3.mp4"));
		assertEquals(".mp4", FileUtils.extractFileName(".mp4"));

		assertEquals("", FileUtils.extractFileName(REMOTE_FILE_PATH_SEPARATOR));
		assertEquals("name", FileUtils.extractFileName(REMOTE_FILE_PATH_SEPARATOR + "name"));
		assertEquals("name.", FileUtils.extractFileName(REMOTE_FILE_PATH_SEPARATOR + "name."));
		assertEquals("name.mp4", FileUtils.extractFileName(REMOTE_FILE_PATH_SEPARATOR + "name.mp4"));
		assertEquals("name.mp3.mp4", FileUtils.extractFileName(REMOTE_FILE_PATH_SEPARATOR + "name.mp3.mp4"));
		assertEquals(".mp4", FileUtils.extractFileName(REMOTE_FILE_PATH_SEPARATOR + ".mp4"));
	}

	@Test
	public void testExtractDirectoryName() {
		assertEquals("", FileUtils.extractDirectoryName(""));
		assertEquals("name", FileUtils.extractDirectoryName("name"));
		assertEquals("name2", FileUtils.extractDirectoryName("name/name1/name2"));
		assertEquals("name1", FileUtils.extractDirectoryName("name/name1/"));
		assertEquals("name.", FileUtils.extractDirectoryName("name."));
		assertEquals("name.mp4", FileUtils.extractDirectoryName("name.mp4"));
		assertEquals("name.mp3.mp4", FileUtils.extractDirectoryName("name.mp3.mp4"));
		assertEquals(".mp4", FileUtils.extractDirectoryName(".mp4"));

		assertEquals("", FileUtils.extractDirectoryName("/"));
		assertEquals("name", FileUtils.extractDirectoryName("/name"));
		assertEquals("name.", FileUtils.extractDirectoryName("/name."));
		assertEquals("name.mp4", FileUtils.extractDirectoryName("/name.mp4"));
		assertEquals("name.", FileUtils.extractDirectoryName("123/name."));
		assertEquals("name.mp4", FileUtils.extractDirectoryName("123/name.mp4"));
		assertEquals("name.mp3.mp4", FileUtils.extractDirectoryName("/name.mp3.mp4"));
		assertEquals(".mp4", FileUtils.extractDirectoryName("/.mp4"));
	}

	@Test
	public void testGetFullTreeFilesListReturnEmptyListIfFileIsNotExist() {
		File file = createMockFileWithName("name");
		when(file.exists()).thenReturn(false);
		List<File> files = FileUtils.getFullTreeFilesList(file);
		assertTrue(files.isEmpty());
	}

	@Test
	public void testGetFullTreeFilesListReturnEmptyListIfFileIsNotDirectory() {
		File file = createMockFileWithName("name");
		List<File> files = FileUtils.getFullTreeFilesList(file);
		assertTrue(files.isEmpty());
	}

	@Test
	public void testGetFullTreeFilesListReturnEmptyListIfDirectoryDoNotHaveChildren() {
		File file = createMockDirectory(null);
		List<File> files = FileUtils.getFullTreeFilesList(file);
		assertTrue(files.isEmpty());
	}

	@Test
	public void testGetFullTreeFilesListReturnFilesListIfFileIsDirectory() {
		File file1 = createMockFileWithName("file1");
		File file2 = createMockFileWithName("file2");
		File file3 = createMockFileWithName("file3");
		File file4 = createMockFileWithName("file4");
		File file5 = createMockFileWithName("file5");
		File directory1 = createMockDirectory(new File[]{file1, file2});
		File directory2 = createMockDirectory(new File[]{file3, file4});
		File rootDirectory = createMockDirectory(new File[]{directory1, directory2, file5});
		List<File> files = FileUtils.getFullTreeFilesList(rootDirectory);

		assertEquals(5, files.size());
		assertTrue(containFileWithName(files, file1.getName()));
		assertTrue(containFileWithName(files, file2.getName()));
		assertTrue(containFileWithName(files, file3.getName()));
		assertTrue(containFileWithName(files, file4.getName()));
		assertTrue(containFileWithName(files, file5.getName()));
	}

	@Test
	public void testGetFullTreeFoldersListReturnEmptyListIfFileIsNotExist() {
		File file = createMockFileWithName("name");
		when(file.exists()).thenReturn(false);
		List<File> files = FileUtils.getFullTreeFoldersList(file);
		assertTrue(files.isEmpty());
	}

	@Test
	public void testGetFullTreeFoldersListReturnEmptyListIfFileIsNotDirectory() {
		File file = createMockFileWithName("name");
		List<File> files = FileUtils.getFullTreeFoldersList(file);
		assertTrue(files.isEmpty());
	}

	@Test
	public void testGetFullTreeFoldersListReturnEmptyListIfDirectoryDoNotHaveChildren() {
		File file = createMockDirectory(null);
		List<File> files = FileUtils.getFullTreeFoldersList(file);
		assertTrue(files.isEmpty());
	}

	@Test
	public void testGetFullTreeFoldersListReturnFilesListIfFileIsDirectory() {
		File file1 = createMockFileWithName("file1");
		File file2 = createMockFileWithName("file2");
		File file3 = createMockFileWithName("file3");
		File file4 = createMockFileWithName("file4");
		File file5 = createMockFileWithName("file5");
		File directory1 = createMockDirectoryWithName("directory1", new File[]{file1, file2});
		File directory2 = createMockDirectoryWithName("directory2", new File[]{file3, file4});
		File rootDirectory = createMockDirectory(new File[]{directory1, directory2, file5});
		List<File> files = FileUtils.getFullTreeFilesList(rootDirectory);

		assertEquals(5, files.size());
		assertTrue(containFileWithName(files, file1.getName()));
		assertTrue(containFileWithName(files, file2.getName()));
		assertTrue(containFileWithName(files, file3.getName()));
		assertTrue(containFileWithName(files, file4.getName()));
		assertTrue(containFileWithName(files, file5.getName()));
	}

	@Test
	public void testGetParentDirPath() {
		assertEquals("", FileUtils.getParentDirPath(""));
		assertEquals("", FileUtils.getParentDirPath("name"));
		assertEquals("", FileUtils.getParentDirPath(File.separator + "name"));
		assertEquals("name", FileUtils.getParentDirPath("name" + File.separator));
		assertEquals("parent", FileUtils.getParentDirPath("parent" + File.separator + "child"));
		assertEquals("parentA" + File.separator + "parentB", FileUtils.getParentDirPath("parentA" + File.separator + "parentB" + File.separator + "child"));
	}

	@Test
	public void testGetCurrentDirName() {
		assertEquals("", FileUtils.getCurrentDirName(""));
		assertEquals("", FileUtils.getCurrentDirName("/"));
		assertEquals("", FileUtils.getCurrentDirName("//"));
		assertEquals("testFolder", FileUtils.getCurrentDirName("testFolder"));
		assertEquals("testFolder", FileUtils.getCurrentDirName("testFolder/"));
		assertEquals("testFolder", FileUtils.getCurrentDirName("/testFolder"));
		assertEquals("testFolder", FileUtils.getCurrentDirName("/testFolder/"));
		assertEquals("1", FileUtils.getCurrentDirName("/testFolder/1"));
		assertEquals("1", FileUtils.getCurrentDirName("/testFolder/1/"));
		assertEquals("1", FileUtils.getCurrentDirName("c:/testFolder/1/"));
	}

	@Test
	public void testPathValid() {
		assertTrue(FileUtils.pathValid("/"));
		assertTrue(FileUtils.pathValid("/a"));
		assertTrue(FileUtils.pathValid("/a/b"));
		assertFalse(FileUtils.pathValid(null));
		assertFalse(FileUtils.pathValid(""));
		assertFalse(FileUtils.pathValid(" "));
		assertFalse(FileUtils.pathValid("a"));
		assertFalse(FileUtils.pathValid("a/"));
		assertFalse(FileUtils.pathValid("a/b"));
		assertFalse(FileUtils.pathValid("//"));
		assertFalse(FileUtils.pathValid("//a"));
		assertFalse(FileUtils.pathValid("//a/b"));
		assertFalse(FileUtils.pathValid("/a//b"));
		assertFalse(FileUtils.pathValid("/a/b/"));
		assertFalse(FileUtils.pathValid(" /a/b"));
	}

	@Test
	public void testPathContainsSubObject() {
		assertTrue(FileUtils.pathContainsSubPath("/a/b", "/a/b/c/c/d"));
		assertTrue(FileUtils.pathContainsSubPath("/a/b/c", "/a/b/c/dc/d"));
		assertTrue(FileUtils.pathContainsSubPath("/a/b/c", "/a/b/c"));
		assertFalse(FileUtils.pathContainsSubPath("/a/b", "/a/bc/c/d"));
		assertFalse(FileUtils.pathContainsSubPath("", ""));
	}

	private File createMockDirectory(File[] listFiles) {
		return createMockDirectory(true, listFiles);
	}

	private File createMockDirectory(boolean exists, File[] listFiles) {
		return createMockFile(exists, true, 0, listFiles);
	}

	private File createMockDirectoryWithName(String name, File[] listFiles) {
		File directory = createMockDirectory(listFiles);
		setName(name, directory);
		return directory;
	}

	private File createMockFile(long length) {
		return createMockFile(true, false, length, null);
	}

	private File createMockFile(boolean exist) {
		return createMockFile(exist, false, 0, null);
	}

	private File createMockFile(boolean exists, boolean isDirectory, long length, File[] listFiles) {
		File file = mock(File.class);
		when(file.exists()).thenReturn(exists);
		when(file.isDirectory()).thenReturn(isDirectory);
		when(file.listFiles()).thenReturn(listFiles);
		when(file.length()).thenReturn(length);
		return file;
	}

	private File createMockFileWithName(String name) {
		File file = createMockFile(0);
		setName(name, file);
		return file;
	}

	private void setName(String name, File file) {
		when(file.getName()).thenReturn(name);
	}
}
