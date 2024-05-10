package com.strato.hidrive.domain.api.bll.file.transformation.public_file;

import com.strato.hidrive.domain.interfaces.actions.Transformation;
import com.strato.hidrive.domain.provider.HidrivePathProvider;
import com.strato.hidrive.domain.utils.FileUtils;

import javax.inject.Inject;

/**
 * Created by yaz on 8/2/16.
 */
public class TeamfolderToLocalFilePathTransformation implements Transformation<String, String> {
	private final HidrivePathProvider pathProvider;

	@Inject
	public TeamfolderToLocalFilePathTransformation(HidrivePathProvider pathProvider) {
		this.pathProvider = pathProvider;
	}

	@Override
	public String transform(String remotePath) {
		String publicFolderPath = this.pathProvider.getTeamfolderPath();
		String localPath = remotePath.equals(publicFolderPath)
				? FileUtils.ROOT_PATH
				: remotePath.replaceFirst(publicFolderPath, "");
		if (!localPath.startsWith(FileUtils.ROOT_PATH)) {
			localPath = FileUtils.ROOT_PATH + localPath;
		}
		return localPath;
	}
}
