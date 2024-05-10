package com.strato.hidrive.domain.api.bll.file.transformation.remote_file;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.strato.hidrive.domain.provider.HidrivePathProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Sergey Shandyuk on 11/14/2017.
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class LocalToRemoteFilePathTransformationTest {
	@Mock
	private HidrivePathProvider pathProvider;
	private LocalToRemoteFilePathTransformation localToRemoteFilePathTransformation;

	@Before
	public void setUp() throws Exception {
		this.localToRemoteFilePathTransformation = new LocalToRemoteFilePathTransformation(pathProvider);
	}

	@After
	public void tearDown() throws Exception {
		this.localToRemoteFilePathTransformation = null;
	}

	@Test
	public void testThatTransformationIsCorrect() throws Exception {
		when(this.pathProvider.getCurrentUserFolderPath()).thenReturn("root/users/alex3");
		assertEquals("root/users/alex3/test", this.localToRemoteFilePathTransformation.transform("/test"));
	}

	@Test
	public void testTransformationWithEmptyLocalPath() throws Exception {
		when(this.pathProvider.getCurrentUserFolderPath()).thenReturn("root/users/alex3");
		assertEquals("root/users/alex3", this.localToRemoteFilePathTransformation.transform(""));
	}
}