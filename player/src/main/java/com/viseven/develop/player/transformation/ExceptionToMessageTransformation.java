package com.viseven.develop.player.transformation;

import com.viseven.develop.player.exception.AudioFocusLostException;
import com.viseven.develop.player.exception.FailRequestAudioFocusException;
import com.viseven.develop.player.exception.FakeMediaPlayerException;
import com.viseven.develop.player.exception.PlayerInitializeException;
import com.viseven.develop.player.exception.SourceException;
import com.viseven.develop.player.interfaces.PlayerExceptionMessageProvider;
import com.viseven.develop.player.interfaces.Transformation;

/**
 * User: zuzik
 * Date: 6/12/16
 */
public class ExceptionToMessageTransformation implements Transformation<Throwable, String> {

	private final PlayerExceptionMessageProvider provider;

	public ExceptionToMessageTransformation(PlayerExceptionMessageProvider provider) {
		this.provider = provider;
	}

	@Override
	public String transform(Throwable throwable) {
		if (throwable instanceof PlayerInitializeException) {
			return this.provider.playerInitializeExceptionMessage();
		} else if (throwable instanceof FailRequestAudioFocusException) {
			return this.provider.failRequestAudioFocusExceptionMessage();
		} else if (throwable instanceof AudioFocusLostException) {
			return this.provider.audioFocusLostExceptionMessage();
		} else if (throwable instanceof FakeMediaPlayerException) {
			return this.provider.fakeExceptionMessage();
		} else if (throwable instanceof SourceException) {
			return this.provider.sourceNotFoundExceptionMessage();
		} else {
			return this.provider.unknownExceptionMessage();
		}
	}
}
