/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO AG.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.ionos.player.ui.message;

import com.ionos.player.model.SourceException;
import com.ionos.player.transformation.Transformation;

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
		if (throwable instanceof SourceException) {
			return this.provider.sourceNotFoundExceptionMessage();
		} else {
			return this.provider.unknownExceptionMessage();
		}
	}
}
