package com.viseven.develop.player.interfaces;

/**
 * User: zuzik
 * Date: 6/12/16
 */
public interface PlayerExceptionMessageProvider {

	String playerInitializeExceptionMessage();

	String failRequestAudioFocusExceptionMessage();

	String audioFocusLostExceptionMessage();

	String fakeExceptionMessage();

	String sourceNotFoundExceptionMessage();

	String unknownExceptionMessage();
}
