package com.strato.hidrive.scanbot.license.oath.security.key;

import java.security.SecureRandom;

/**
 * Created by Sergey Shandyuk on 8/16/2018.
 */
public class AesGcmKeyGenerator {

	public final AesGcmKey generate() {
		int keyLength = 16;
		int ivLength = 12;
		return new AesGcmKey(generateRandomSecure(keyLength), generateRandomSecure(ivLength));
	}

	private byte[] generateRandomSecure(int length) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[length];
		secureRandom.nextBytes(key);
		return key;
	}
}
