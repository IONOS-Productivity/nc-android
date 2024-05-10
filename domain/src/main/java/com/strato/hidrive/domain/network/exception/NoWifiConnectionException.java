package com.strato.hidrive.domain.network.exception;

/**
 * Created by yaz on 11/22/16.
 */

public class NoWifiConnectionException extends ConnectionException {
	public NoWifiConnectionException() {
		super("Missing needed WiFi connection");
	}
}
