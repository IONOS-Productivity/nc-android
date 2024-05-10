package com.strato.hidrive.domain.hash;


public class FileInfoUtil {
	private static final int MILLISECONDS_IN_SECOND = 1000;

	private FileInfoUtil() {
	}

	public static String mHash(boolean isDirectory, String name, long contentLength, long lastModified) {
		if (isDirectory) {
			return getDirMHash(name, lastModified);
		} else {
			return getFileMHash(name, contentLength, lastModified);
		}
	}

	private static String getFileMHash(String name, long contentLength, long lastModified) {
		return Sha1Hash.mHash(name, contentLength, lastModified / MILLISECONDS_IN_SECOND);
	}

	private static String getDirMHash(String name, long lastModified) {
		return Sha1Hash.mHash(name, lastModified / MILLISECONDS_IN_SECOND);
	}
}
