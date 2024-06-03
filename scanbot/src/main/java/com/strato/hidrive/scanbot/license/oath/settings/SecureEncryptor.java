package com.strato.hidrive.scanbot.license.oath.settings;

import android.util.Base64;

import com.ionos.logger.LoggerUtil;
import com.strato.hidrive.scanbot.license.oath.security.algorithm.EncryptionAlgorithm;

/**
 * Created by y.zozulia on 18.11.2015.
 */

public class SecureEncryptor {
	private final EncryptionAlgorithm encryptionAlgorithm;

	public SecureEncryptor(EncryptionAlgorithm encryptionAlgorithm) {
		this.encryptionAlgorithm = encryptionAlgorithm;
	}

	public String decrypt(String value) throws Exception {
		return this.encryptionAlgorithm.decrypt(Base64.decode(value, Base64.DEFAULT));
	}

	public String encrypt(String value) {
		String encryptedValue = "";
		try {
			encryptedValue = Base64.encodeToString(this.encryptionAlgorithm.encrypt(value), Base64.DEFAULT);
		} catch (Exception e) {
			LoggerUtil.logE(getClass().getSimpleName(), e);
		}
		return encryptedValue;
	}
}
