package com.strato.hidrive.common_ui.intent;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.strato.hidrive.domain.interfaces.actions.Action;

/**
 * User: Dima Muravyov
 * Date: 11.07.2016
 */
public class OpenSettingsAction implements Action {

	private final Context context;

	public OpenSettingsAction(Context context) {
		this.context = new ContextWrapper(context).getApplicationContext();
	}

	@Override
	public void execute() {
		Uri uri = Uri.parse("package:" + context.getPackageName());
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.context.startActivity(intent);
	}
}
