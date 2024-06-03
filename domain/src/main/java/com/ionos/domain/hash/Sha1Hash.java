package com.ionos.domain.hash;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Sha1Hash {
	private static final int BUFFER_LENGTH_BYTES = 8;

	//DigestUtils methods with "Hex" can't be used. They call Hex.encodeHexString(), it crash the app.

	public static String mHash(String name, long size, long mTime) {
		byte[] inputData = concatArrays(DigestUtils.sha1(name), len64(size), len64(mTime));
		return new String(Hex.encodeHex(DigestUtils.sha1(inputData)));
	}

	public static String mHash(String name, long mTime) {
		byte[] inputData = ArrayUtils.addAll(DigestUtils.sha1(name), len64(mTime));
		return new String(Hex.encodeHex(DigestUtils.sha1(inputData)));
	}

	private static byte[] len64(long size) {
		return ByteBuffer
				.allocate(BUFFER_LENGTH_BYTES)
				.order(ByteOrder.LITTLE_ENDIAN)
				.putLong(size)
				.array();
	}

	private static byte[] concatArrays(byte[] first, byte[] second, byte[] third) {
		return ArrayUtils.addAll(ArrayUtils.addAll(first, second), third);
	}
}
