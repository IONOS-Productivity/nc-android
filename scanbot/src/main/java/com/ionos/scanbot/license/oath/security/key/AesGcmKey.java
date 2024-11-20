package com.ionos.scanbot.license.oath.security.key;


public class AesGcmKey {
	public final byte[] key;
	public final byte[] iv;

	public AesGcmKey(byte[] key, byte[] iv) {
		this.key = key;
		this.iv = iv;
	}
}
