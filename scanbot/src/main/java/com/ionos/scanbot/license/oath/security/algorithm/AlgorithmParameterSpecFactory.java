package com.ionos.scanbot.license.oath.security.algorithm;

import java.security.spec.AlgorithmParameterSpec;

/**
 * Created by Sergey Shandyuk on 8/20/2018.
 */
public interface AlgorithmParameterSpecFactory {
	AlgorithmParameterSpec create(byte[] initializationVector);
}
