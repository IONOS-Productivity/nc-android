package com.ionos.domain.utils;

import static com.ionos.domain.utils.Preconditions.checkNotNull;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {
	public static final long B = 1;
	public static final long Kb = B * 1024;
	public static final long Mb = Kb * 1024;
	public static final long Gb = Mb * 1024;
	public static final long Tb = Gb * 1024;

	public static final String MODIFY_SUFFIX = "_modified";
	private static final String EXTENSION_SEPARATOR = ".";
	public static final char EXTENSIONS_SEPARATOR = '.';
	public static final String PATH_SEPARATOR = "/";
	private static final String LEFT_PARENTHESIS = "(";
	private static final String RIGHT_PARENTHESIS = ")";

	public static final String ROOT_PATH = "/";

	private FileUtils() {
		throw new AssertionError();
	}


	public static String correctPath(String path) {
		if (!path.endsWith("/")) {
			return path + "/";
		}
		return path;
	}

	public static boolean isValidName(String text) {
		Pattern pattern = Pattern.compile("^[^/\\\\:*?\"<>|%.][^/\\\\:*?\\\"<>|%]*$");
		Matcher matcher = pattern.matcher(text);

		boolean isMatch = matcher.matches();
		boolean isWhitespaceName = text.matches("^\\s*$");

		return isMatch && !isWhitespaceName;
	}

	public static boolean deleteDir(File dir) {
		if (dir == null) {
			return false;
		}

		if (dir.exists() && dir.isDirectory()) {
			String[] children = dir.list();
			if (children == null) {
				return dir.delete(); //dir is not directory.
			}
			for (String child : children) {
				boolean success = deleteDir(new File(dir, child));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static long getDirectorySize(File dir) {
		long size = 0;
		if (dir.exists() && dir.isDirectory()) {
			File[] children = dir.listFiles();
			if (children != null) {
				for (File child : children) {
					if (child.isDirectory()) {
						size += getDirectorySize(child);
					} else {
						size += child.length();
					}
				}
			}
		}
		return size;
	}

	public static long getFileListSize(List<File> fileList) {
		long size = 0;
		for (File file : fileList) {
			if (!file.isDirectory()) {
				size += file.length();
			}
		}
		return size;
	}

	public static void deleteFileOrDir(File file) {
		if (file == null) {
			return;
		}
		if (file.exists()) {
			if (file.isDirectory()) {
				deleteDir(file);
			} else {
				file.delete();
			}
		}
	}

	public static void deleteFile(String path) {
		deleteFile(new File(path));
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			file.delete();
		}
	}

	public static long getFileOrDirSize(File fileOrDirectory) {
		return fileOrDirectory.isDirectory() ? getDirectorySize(fileOrDirectory) : fileOrDirectory.length();
	}

	// adds to files all directory child files and parent directory himself
	public static void addAllDirectoryFiles(File dir, List<File> files) {
		if (dir.exists() && dir.isDirectory()) {
			files.add(dir);
			File[] childrens = dir.listFiles();
			if (childrens != null) {
				for (File child : childrens) {
					if (child.isDirectory()) {
						addAllDirectoryFiles(child, files);
					} else {
						files.add(child);
					}
				}
			}
		}
	}

	public static boolean isFileNameHasExtension(String fileName) {
		int indexOfSeparator = fileName.lastIndexOf(EXTENSION_SEPARATOR);
		return indexOfSeparator != -1
				&& indexOfSeparator != 0
				&& indexOfSeparator != fileName.length() - 1;
	}

	public static String makeNonConflictingFileName(String originalName, int index) {
		if (isFileNameHasExtension(originalName)) {
			int dotPos = originalName.lastIndexOf(EXTENSION_SEPARATOR);
			return originalName.substring(0, dotPos) + LEFT_PARENTHESIS + index + RIGHT_PARENTHESIS + originalName.substring(dotPos);
		} else {
			return originalName + LEFT_PARENTHESIS + index + RIGHT_PARENTHESIS;
		}
	}

	@NonNull
	public static String makeFileNameWithSuffix(@NonNull String originalName, @NonNull String suffix){
		if (isFileNameHasExtension(originalName)) {
			int dotPos = originalName.lastIndexOf(EXTENSION_SEPARATOR);
			return originalName.substring(0, dotPos) + suffix + originalName.substring(dotPos);
		} else {
			return originalName + suffix;
		}
	}

	public static String makeFileNameWithModifySuffix(String originalName) {
		return makeFileNameWithSuffix(originalName, MODIFY_SUFFIX);
	}

	public static String extractFileNameWithoutExtension(String fullPath) {
		String fileName = extractFileName(fullPath);
		int rightBorder = isFileNameHasExtension(fileName) ? fileName.lastIndexOf(EXTENSIONS_SEPARATOR) : fileName.length();
		return fileName.substring(0, rightBorder);
	}

	public static String extractFileName(String fullPath) {
		if (fullPath == null) {
			return "";
		}

		int separatorPlace = fullPath.lastIndexOf(PATH_SEPARATOR);
		if (separatorPlace != -1) {
			return fullPath.substring(fullPath.lastIndexOf(PATH_SEPARATOR) + 1);
		} else {
			return fullPath;
		}
	}

	public static String extractDirectoryName(String path) {
		return new File(path).getName();
	}

	public interface ErrorCallback{
		void invoke(Exception e);
	}

	public static String readFileAsString(File file, ErrorCallback errorCallback) {
		StringBuilder text = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				text.append(line);
			}
		} catch (IOException e) {
			errorCallback.invoke(e);
		}
		return text.toString();
	}

	public static List<File> getFullTreeFilesList(File dir) {
		List<File> files = new ArrayList<>();
		if (dir.exists() && dir.isDirectory()) {
			File[] childrens = dir.listFiles();
			if (childrens != null) {
				files.addAll(Arrays.asList(childrens));
				int i = 0;
				while (i < files.size()) {
					File file = files.get(i);
					if (file.isDirectory()) {
						files.remove(i);
						childrens = file.listFiles();
						if (childrens != null) {
							files.addAll(Arrays.asList(childrens));
						}
					} else {
						i++;
					}
				}
			}
		}
		return files;
	}

	public static List<File> getFullTreeFoldersList(File dir) {
		List<File> files = new ArrayList<>();
		if (dir.exists() && dir.isDirectory()) {
			File[] childrens = dir.listFiles();
			if (childrens != null) {
				files.addAll(Arrays.asList(childrens));
				int i = 0;
				while (i < files.size()) {
					File file = files.get(i);
					if (file.isDirectory()) {
						childrens = file.listFiles();
						if (childrens != null) {
							files.addAll(Arrays.asList(childrens));
						}
						i++;
					} else {
						files.remove(i);
					}
				}
			}
		}
		return files;
	}

	@SuppressWarnings("SimpleDateFormat")
	public static String generateFileName(String destPath, String prefix, String extension) {
		String timePart = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
		prefix = prefix.replaceAll("[\\/:*?\"<>|]", "");
		return destPath + File.separator + prefix + "-" + timePart + "." + extension;
	}

	@NonNull
	public static String getParentDirPath(@NonNull String fileOrDirPath) {
		if (!fileOrDirPath.isEmpty() && fileOrDirPath.contains(File.separator)) {
			boolean endsWithSlash = fileOrDirPath.endsWith(File.separator);
			if (endsWithSlash) {
				return fileOrDirPath.substring(0, fileOrDirPath.length() - 1);
			} else {
				return fileOrDirPath.substring(0, fileOrDirPath.lastIndexOf(File.separatorChar));
			}
		} else {
			return "";
		}
	}

	public static File generateZIPDestFileName(String destPath, String basePath, String appName) {
		String folderName = basePath.substring(basePath.lastIndexOf(File.separator) + 1);
		String prefix = String.format("%s-%s", appName, folderName);
		return new File(generateFileName(destPath, prefix, "zip"));
	}

	public static String getCurrentDirName(String path) {
		final String[] folders = path.split(PATH_SEPARATOR);
		return folders.length > 0 ?
				folders[folders.length - 1] :
				"";
	}

	public static String normalizeRemotePath(String path) {
		if (Character.toString(path.charAt(path.length() - 1)).equals(PATH_SEPARATOR)) {
			return path.substring(0, path.length() - 1);
		} else {
			return path;
		}
	}

	public static boolean pathValid(String path) {
		if (path == null) {
			return false;
		}

		for (int i = 0; i < path.length(); i++) {
			boolean currentSymbolIsSeparator = PATH_SEPARATOR.equals(String.valueOf(path.charAt(i)));
			boolean previousSymbolIsSeparator = i > 0 && PATH_SEPARATOR.equals(String.valueOf(path.charAt(i - 1)));
			if (currentSymbolIsSeparator && previousSymbolIsSeparator) {
				return false;
			}
		}

		int firstIndexOfSeparator = path.indexOf(PATH_SEPARATOR);
		int lastIndexOfSeparator = path.lastIndexOf(PATH_SEPARATOR);

		boolean containSeparators = firstIndexOfSeparator != -1 && lastIndexOfSeparator != -1;
		if (!containSeparators) {
			return false;
		}
		boolean startsWithSeparator = firstIndexOfSeparator == 0;
		boolean endsWithSeparator = lastIndexOfSeparator == path.length() - 1;
		boolean isRoot = firstIndexOfSeparator == 0 && lastIndexOfSeparator == 0;

		return isRoot || (startsWithSeparator && !endsWithSeparator);
	}

	public static boolean pathContainsSubPath(String path, String subPath) {
		String rootSlashedPath = path + PATH_SEPARATOR;
		String slashedSubPath = subPath + PATH_SEPARATOR;
		return (!path.isEmpty() && !subPath.isEmpty()) && slashedSubPath.startsWith(rootSlashedPath);
	}

	public static long copyBetweenStreams(InputStream from, OutputStream to)
			throws IOException {
		final int BUF_SIZE = 0x1000;

		checkNotNull(from);
		checkNotNull(to);
		byte[] buf = new byte[BUF_SIZE];
		long total = 0;
		int r;
		while ((r = from.read(buf)) != -1) {
			to.write(buf, 0, r);
			total += r;
		}
		return total;
	}

	public static File getOrCreateDirectory(String path) {
		File dir = new File(path);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		return dir;
	}

	public static String concatPath(String... parts) {
		StringBuilder partsBuilder = new StringBuilder();
		for (int i = 0; i < parts.length; i ++) {
			partsBuilder.append(parts[i]);
			if (!parts[i].endsWith(PATH_SEPARATOR) && i < parts.length - 1) {
				partsBuilder.append(PATH_SEPARATOR);
			}
		}
		return partsBuilder.toString();
	}
}