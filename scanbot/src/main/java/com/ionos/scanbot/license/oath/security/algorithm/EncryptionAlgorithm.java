package com.ionos.scanbot.license.oath.security.algorithm;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by Sergey Shandyuk on 8/16/2018.
 */
public interface EncryptionAlgorithm {
	byte[] encrypt(String data) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException;

	String decrypt(byte[] data) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException;
}
