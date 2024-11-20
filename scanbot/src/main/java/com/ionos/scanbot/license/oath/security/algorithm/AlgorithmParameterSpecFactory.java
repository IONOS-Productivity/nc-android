package com.ionos.scanbot.license.oath.security.algorithm;

import java.security.spec.AlgorithmParameterSpec;


public interface AlgorithmParameterSpecFactory {
	AlgorithmParameterSpec create(byte[] initializationVector);
}
