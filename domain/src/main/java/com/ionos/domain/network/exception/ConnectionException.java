package com.ionos.domain.network.exception;

/**
 * Created by: Denis Botvin
 * Date: 05.03.2020
 */
public abstract class ConnectionException extends Exception {
	ConnectionException(String message) {
		super(message);
	}
}
