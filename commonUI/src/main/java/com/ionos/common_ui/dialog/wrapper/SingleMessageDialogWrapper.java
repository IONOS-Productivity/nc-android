package com.ionos.common_ui.dialog.wrapper;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

import com.ionos.common_ui.dialog.stylized.localized.LocalizedTextStrategy;
import com.ionos.common_ui.dialog.stylized.localized.StringLocalizedStrategy;
import com.ionos.common_ui.dialog.stylized.localized.StringResLocalizedStrategy;
import com.ionos.common_ui.R;
import com.ionos.common_ui.dialog.stylized.StylizedDialog;
import com.ionos.common_ui.exception.TryCatchExceptionHandler;
import com.ionos.common_ui.dialog.actions.Action;

/**
 * Created by Sergey Shandyuk on 12/30/2016.
 */

public class SingleMessageDialogWrapper {
	private final StylizedDialog dialog;

	public SingleMessageDialogWrapper(Context context,
									  TryCatchExceptionHandler tryCatchExceptionHandler,
									  @StringRes int title,
									  @StringRes int message,
									  @StringRes int okBtnTitle,
									  @StringRes int cancelBtnTitle,
									  final Action onPositiveClick,
									  final Action onNegativeClick) {
		this.dialog = createDialog(
				context,
				tryCatchExceptionHandler,
				new StringResLocalizedStrategy(context, title),
				new StringResLocalizedStrategy(context, message),
				new StringResLocalizedStrategy(context, okBtnTitle),
				new StringResLocalizedStrategy(context, cancelBtnTitle),
				onPositiveClick,
				onNegativeClick,
				R.color.accent_color);
	}

	public SingleMessageDialogWrapper(Context context,
									  TryCatchExceptionHandler tryCatchExceptionHandler,
									  String title,
									  String message,
									  @StringRes int okBtnTitle,
									  @StringRes int cancelBtnTitle,
									  final Action onPositiveClick,
									  final Action onNegativeClick) {
		this(
				context, tryCatchExceptionHandler, title, message,
				okBtnTitle, cancelBtnTitle, onPositiveClick, onNegativeClick, R.color.accent_color
		);
	}
	public SingleMessageDialogWrapper(Context context,
									  TryCatchExceptionHandler tryCatchExceptionHandler,
									  String title,
									  String message,
									  @StringRes int okBtnTitle,
									  @StringRes int cancelBtnTitle,
									  final Action onPositiveClick,
									  final Action onNegativeClick,
									  @ColorRes  int buttonTextColor
									  ) {
		this.dialog = createDialog(
				context,
				tryCatchExceptionHandler,
				new StringLocalizedStrategy(title),
				new StringLocalizedStrategy(message),
				new StringResLocalizedStrategy(context, okBtnTitle),
				new StringResLocalizedStrategy(context, cancelBtnTitle),
				onPositiveClick,
				onNegativeClick,
				buttonTextColor);
	}

	private StylizedDialog createDialog(Context context,
										TryCatchExceptionHandler tryCatchExceptionHandler,
										LocalizedTextStrategy title,
										LocalizedTextStrategy message,
										LocalizedTextStrategy okBtnTitle,
										LocalizedTextStrategy cancelBtnTitle,
										final Action onPositiveClick,
										final Action onNegativeClick,
										int buttonTextColor) {
		return StylizedDialog
				.newBuilder(tryCatchExceptionHandler)
				.setTitle(title)
				.setMessage(message)
				.setPositiveButton(
						okBtnTitle,
						dialog -> {
							onPositiveClick.execute();
							dialog.dismiss();
						})
				.setNegativeButton(
						cancelBtnTitle,
						dialog -> {
							onNegativeClick.execute();
							dialog.dismiss();
						})
				.setButtonsTextColor(buttonTextColor)
				.setCancelable(false)
				.build(context);
	}

	public final void show() {
		this.dialog.show();
	}

	public final void hide() {
		this.dialog.dismiss();
	}
}