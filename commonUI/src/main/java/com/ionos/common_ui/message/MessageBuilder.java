package com.ionos.common_ui.message;

import android.content.Context;

import androidx.annotation.StringRes;

import com.ionos.domain.interfaces.actions.Action;

/**
 * User: Dima Muravyov
 * Date: 11.07.2016
 */
public interface MessageBuilder {
	MessageBuilder setText(@StringRes int messageRes);

	MessageBuilder setText(String message);

	MessageBuilder setDuration(Duration duration);

	MessageBuilder setAction(String buttonText, Action action);

	MessageBuilder setOnDismissAction(Action onDismissAction);

	Message build(Context context);
}
