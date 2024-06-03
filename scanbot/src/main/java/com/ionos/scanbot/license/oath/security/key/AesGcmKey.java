package com.ionos.scanbot.license.oath.security.key;

/**
 * Created by Sergey Shandyuk on 8/16/2018.
 */
public class AesGcmKey {
	public final byte[] key;
	public final byte[] iv;

	public AesGcmKey(byte[] key, byte[] iv) {
		this.key = key;
		this.iv = iv;
	}
}
