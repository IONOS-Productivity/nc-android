package com.ionos.common_ui.dialog.stylized.localized;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Created by Sergey Shandyuk on 7/29/2016.
 */
public class ArgsLocalizedTextStrategy implements LocalizedTextStrategy {
	private final Context context;
	@StringRes private final int format;
	private final Object[] args;

	public ArgsLocalizedTextStrategy(Context context, @StringRes int format, @NonNull Object... args) {
		this.context = context;
		this.format = format;
		this.args = args;
	}

	private Object[] prepareArgs() {
		Object[] preparedArgs = new Object[this.args.length];

		for (int i = 0; i < this.args.length; i++) {
			Object obj = this.args[i];

			if (obj instanceof String) {
				preparedArgs[i] = obj;
			} else {
				preparedArgs[i] = this.context.getString((int) obj);
			}
		}
		return preparedArgs;
	}

	public String getLocalizedString() {
		return String.format(this.context.getString(this.format), prepareArgs());
	}
}
