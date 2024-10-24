package com.ionos.scanbot.license.oath.security.algorithm;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.spec.GCMParameterSpec;

/**
 * Created by Sergey Shandyuk on 8/20/2018.
 */
public class AlgorithmParameterSpecFactoryImpl implements AlgorithmParameterSpecFactory {

	@Override
	public AlgorithmParameterSpec create(byte[] initializationVector) {
		return createParams(initializationVector, initializationVector.length);
	}

	private AlgorithmParameterSpec createParams(final byte[] buf, int len) {
		int algorithmLength = 128;
		return new GCMParameterSpec(algorithmLength, buf, 0, len);
	}
}
