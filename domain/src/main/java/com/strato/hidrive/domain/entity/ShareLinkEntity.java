/**
 * Copyright 2014 STRATO AG
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.strato.hidrive.domain.entity;

import com.strato.hidrive.domain.hash.FileInfoUtil;
import com.strato.hidrive.domain.utils.FileUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;

/**
 * A entity that describes a sharelink information associated with an remote file or directory.
 */
public class ShareLinkEntity implements Serializable {

	public enum FileType {
		FILE,
		DIR
	}

	public enum Status {
		VALID("valid"),
		INVALID("invalid"),
		EXPIRED("expired");

		@NotNull
		private final String key;

		Status(@NotNull String key) {
			this.key = key;
		}

		@NotNull
		public static Status getByValue(String value) throws IllegalArgumentException {
			for (Status type : Status.values()) {
				if (type.key.equals(value)) {
					return type;
				}
			}
			throw new IllegalArgumentException("Unknown ShareLinkEntity.Status value");
		}
	}

	public static final long UNLIMITED_VALUE = 0;
	public static final int MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

	private static final long serialVersionUID = -4280878155095197915L;
	private static final int MILLISECONDS_IN_SECOND = 1000;

	private final long createdTimestamp;
	private long timeToLive;
	private final long lastModifiedTimestamp;
	private long downloadMaxCount;
	private final long downloadsCount;

	private final String downloadUri;
	private String path;
	private final String id;
	private String password;
	private final Status status;

	private boolean writable;
	private final FileType fileType;
	private final String pid;
	private final long valid_until;
	private final boolean readable;
	private final long remaining;
	private boolean has_password;

	private transient RemoteFileInfo file;

	/**
	 * Constructs instance
	 */
	public ShareLinkEntity(long createdTimestamp,
						   @NotNull String path,
						   long timeToLive,
						   long downloadMaxCount,
						   long downloadsCount,
						   @NotNull String downloadUri,
						   @NotNull String id,
						   long lastModifiedTimestamp,
						   @NotNull String password,
						   @NotNull Status status,
						   boolean writable,
						   @NotNull FileType fileType,
						   @NotNull String pid,
						   long validUntil,
						   boolean readable,
						   long remaining,
						   boolean hasPassword,
						   @NotNull String fileName,
						   long fileSize,
						   @Nullable ImageInfo imageInfo) {
		this.createdTimestamp = createdTimestamp * MILLISECONDS_IN_SECOND;
		this.path = path;
		this.timeToLive = timeToLive * MILLISECONDS_IN_SECOND;
		this.downloadMaxCount = downloadMaxCount;
		this.downloadsCount = downloadsCount;
		this.downloadUri = downloadUri;
		this.id = id;
		this.lastModifiedTimestamp = lastModifiedTimestamp * MILLISECONDS_IN_SECOND;
		this.password = password;
		this.status = status;

		this.writable = writable;
		this.fileType = fileType;
		this.pid = pid;
		this.valid_until = validUntil;
		this.readable = readable;
		this.remaining = remaining;
		this.has_password = hasPassword;

		this.file = new RemoteFileInfo(
				this.path,
				this.pid,
				getName(path, fileName),
				isDirectory(),
				this.lastModifiedTimestamp,
				this.createdTimestamp,
				fileSize,
				false,
				false,
				false,
				0,
				FileInfoUtil.mHash(isDirectory(), getName(path, fileName), fileSize, lastModifiedTimestamp),
				imageInfo);
	}

	public ShareLinkEntity(ShareLinkEntity shareLinkEntity) {
		this.createdTimestamp = shareLinkEntity.createdTimestamp;
		this.path = shareLinkEntity.path;
		this.timeToLive = shareLinkEntity.timeToLive;
		this.downloadMaxCount = shareLinkEntity.downloadMaxCount;
		this.downloadsCount = shareLinkEntity.downloadsCount;
		this.downloadUri = shareLinkEntity.downloadUri;
		this.id = shareLinkEntity.id;
		this.lastModifiedTimestamp = shareLinkEntity.lastModifiedTimestamp;
		this.password = shareLinkEntity.password;
		this.status = shareLinkEntity.status;

		this.writable = shareLinkEntity.writable;
		this.fileType = shareLinkEntity.fileType;
		this.pid = shareLinkEntity.pid;
		this.valid_until = shareLinkEntity.valid_until;
		this.readable = shareLinkEntity.readable;
		this.remaining = shareLinkEntity.remaining;
		this.has_password = shareLinkEntity.has_password;
		this.file = shareLinkEntity.getFile();
	}

	/**
	 * Get associated remote file info if any (api not guarantee that this was set)
	 *
	 * @return associated file info or null
	 */
	public RemoteFileInfo getFile() {
		return file;
	}

	/**
	 * Sets associated file info
	 *
	 * @param file file info to associate
	 */
	public ShareLinkEntity setFile(RemoteFileInfo file) {
		this.file = file;
		return this;
	}

	/**
	 * Get share expiry.
	 *
	 * @return share expiry. A positive number defining milliseconds from now.
	 */
	public long getTimeToLive() {
		return timeToLive;
	}

	/**
	 * Get share expiry in seconds
	 *
	 * @return share expiry. A positive number defining seconds from now.
	 */
	public long getTimeToLiveInSeconds() {
		return timeToLive / MILLISECONDS_IN_SECOND;
	}

	/**
	 * Total number of allowed successful downloads.
	 */
	public long getDownloadMaxCount() {
		return this.downloadMaxCount;
	}

	/**
	 * Get number of successfully completed downloads so far.
	 *
	 * @return number of successfully completed downloads so far.
	 */
	public long getDownloadsCount() {
		return this.downloadsCount;
	}

	/**
	 * Calculate remaining allowed downloads.
	 *
	 * @return remaining allowed downloads
	 */
	public long getDownloadRemaining() {
		return this.downloadMaxCount - this.downloadsCount;
	}

	/**
	 * Get download URI for the sharelink.
	 *
	 * @return download URI for the sharelink. Only given on valid sharelinks
	 */
	public String getDownloadUri() {
		return this.downloadUri;
	}

	/**
	 * Get path to the linked file.
	 *
	 * @return path to the linked file or directory.
	 */
	public String getPath() {
		return this.path;
	}

	/**
	 * Get public path id to the linked file.
	 *
	 * @return public path id to the linked file.
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Get password for "private" sharelinks.
	 *
	 * @return clear text password for "private" sharelinks.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Check is sharelink protected by password.
	 *
	 * @return true if sharelink protected with an password.
	 */
	public Boolean hasPasswordProtection() {
		return (this.password != null && this.password.length() != 0) || has_password;
	}

	/**
	 * Get sharelink status.
	 *
	 * @return can have one of three states: "valid": Linked file is ready for download "expired": Sharelink has exceeded a limit (ttl or download count) "invalid": The file of the sharelink does not exist.
	 */
	public Status getStatus() {
		return this.status;
	}

	/**
	 * Get sharelink creation time
	 *
	 * @return UNIX time stamp (seconds since the epoch) when the sharelink has been created.
	 */
	public long getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	/**
	 * Get sharelink creation time
	 *
	 * @return UNIX time stamp (seconds since the epoch) when the sharelink has been created.
	 */
	public long getCreatedTimestampInSeconds() {
		return this.createdTimestamp / MILLISECONDS_IN_SECOND;
	}

	/**
	 * Get user-readable sharelink creation time
	 *
	 * @return user-readable sharelink creation time
	 */
	public String getCreatedDateDescription() {
		return getDateDescription(createdTimestamp);
	}

	/**
	 * Get user-readable sharelink downloads expires description
	 *
	 * @return user-readable sharelink downloads expires description
	 */
	public String getDownloadsExpiresDateDescription() {
		return getDateDescription(this.createdTimestamp + this.timeToLive);
	}

	/**
	 * Get sharelink last modification time
	 *
	 * @return sharelink last modification time
	 */
	public long getLastModifiedTimestampInSeconds() {
		return this.lastModifiedTimestamp / MILLISECONDS_IN_SECOND;
	}

	/**
	 * Get UNIX timestamp
	 *
	 * @return UNIX timestamp
	 */
	public long getValidUntilInSeconds() {
		return this.valid_until;
	}

	/**
	 * Is share link readable
	 *
	 * @return readable value
	 */
	public boolean getReadable() {
		return this.readable;
	}

	/**
	 * Get number of remaining available share tokens
	 *
	 * @return number of remaining available share tokens
	 */
	public long getRemaining() {
		return this.remaining;
	}

	/**
	 * Get user-readable sharelink last modification time
	 *
	 * @return user-readable sharelink last modification time
	 */
	public String getLastModifiedDateDescription() {
		return getDateDescription(this.lastModifiedTimestamp);
	}

	private String getDateDescription(long timestamp) {
		return new Date(timestamp).toLocaleString();
	}

	/**
	 * Calculate number of days from now while sharelink will remains valid
	 *
	 * @return number of days from now while sharelink will remains valid
	 */
	public int getValidDays() {
		long days = this.timeToLive / (MILLISECONDS_IN_DAY);
		if (this.timeToLive % (MILLISECONDS_IN_DAY) > 0) {
			days++;
		}
		if (days < 0) {
			return 0;
		}
		return (int) days;
	}

	public void setValidDays(int days) {
		this.timeToLive = (long) days * MILLISECONDS_IN_DAY;
	}

	/**
	 * Set associated file path
	 *
	 * @param path associated file path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Check is sharelink is valid
	 *
	 * @return true if sharelink is valid
	 */
	public boolean isSharelinkValid() {
		return Status.VALID == this.status || Status.EXPIRED == this.status;
	}

	/**
	 * Get type of associated shared object
	 *
	 * @return type description of associated shared object
	 */
	public FileType getFileType() {
		return fileType;
	}

	/**
	 * The public id of the shared file.
	 *
	 * @return public id of the shared file.
	 */
	public String getPid() {
		return pid;
	}

	public boolean isWritable() {
		return this.writable;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof ShareLinkEntity && id.equals(((ShareLinkEntity) o).id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	public void setPassword(String password) {
		this.password = password;
		this.has_password = !isEmpty(password);
	}

	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	public void setDownloadMaxCount(long downloadMaxCount) {
		this.downloadMaxCount = downloadMaxCount;
	}

	public void setWritable(boolean writable) {
		this.writable = writable;
	}

	public boolean isUnlimitedDownloads() {
		return this.downloadMaxCount == UNLIMITED_VALUE;
	}

	public void setUnlimitedDownloads() {
		this.downloadMaxCount = UNLIMITED_VALUE;
	}

	public boolean isUnlimitedTimeToLive() {
		return Status.VALID == this.status && this.timeToLive == UNLIMITED_VALUE;
	}

	public void setUnlimitedTimeToLive() {
		this.timeToLive = UNLIMITED_VALUE;
	}

	public boolean isDirectory() {
		return FileType.DIR == this.fileType;
	}

	private boolean isEmpty(String text) {
		return text == null || text.length() == 0;
	}

	private String getName(@NotNull String path, @NotNull String fileName) {
		return fileName.isEmpty() ? FileUtils.getCurrentDirName(path) : fileName;
	}
}
