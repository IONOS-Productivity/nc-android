package com.strato.hidrive.domain.utils

import com.strato.hidrive.domain.entity.RemoteFileInfo
import org.junit.Assert
import org.junit.Test


class ToTopLevelPathsTransformationTest{

	private val inputData = listOf(
		RemoteFileInfo(path = "/folder 1", isDirectory = true),
		RemoteFileInfo(path = "/folder 1/a.jpg", isDirectory = false),
		RemoteFileInfo(path = "/folder 1/b.jpg", isDirectory = false),
		RemoteFileInfo(path = "/folder 1/inner", isDirectory = true),
		RemoteFileInfo(path = "/folder 1/inner/a.jpg", isDirectory = false),

		RemoteFileInfo(path = "/folder 2", isDirectory = true),
		RemoteFileInfo(path = "/folder 2/a.jpg", isDirectory = false),
		RemoteFileInfo(path = "/folder 2/b.jpg", isDirectory = false),
		RemoteFileInfo(path = "/folder 2/inner", isDirectory = true),
		RemoteFileInfo(path = "/folder 2/inner/a.jpg", isDirectory = false),
	)
		.shuffled()

	private val transformation = ToTopLevelPathsTransformation()

	@Test
	fun test() {
		val result = transformation.transform(inputData)
			.blockingGet()

		Assert.assertEquals(
			2,
			result.size
		)
	}

}