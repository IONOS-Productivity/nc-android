package com.ionos.common_ui.dialog.wrapper;

import android.content.Context;

import com.ionos.common_ui.dialog.stylized.localized.StringResLocalizedStrategy;
import com.ionos.common_ui.R;
import com.ionos.common_ui.di.CommonUiComponent;
import com.ionos.common_ui.dialog.stylized.StylizedDialog;
import com.ionos.common_ui.utils.ContextUtils;
import com.ionos.common_ui.exception.TryCatchExceptionHandler;

import javax.inject.Inject;

/**
 * Created by Sergey Shandyuk on 12/30/2016.
 */

public class NoFreeSpaceMessageDialogWrapper {
    //TODO alk
//	@Inject
//	ICustomFonts customFonts;
	@Inject
	TryCatchExceptionHandler tryCatchExceptionHandler;

	private final StylizedDialog dialog;

	public NoFreeSpaceMessageDialogWrapper(Context context) {
		CommonUiComponent.from(context).inject(this);

		this.dialog = StylizedDialog.newBuilder(this.tryCatchExceptionHandler)
				.setTitle(new StringResLocalizedStrategy(context, R.string.no_enough_free_device_space_title))
				.setMessage(new StringResLocalizedStrategy(context, R.string.scanbot_no_enough_free_device_space_message))
				.setPositiveButton(new StringResLocalizedStrategy(context, R.string.ok_btn_title), StylizedDialog::dismiss)
				.setButtonsTextColor(R.color.stylized_edit_text_line_color)
//				.setTypeface(this.customFonts.getMainRegularFont())
				.build(context);
	}

	public final void show(Context context) {
		if (!ContextUtils.isActivityFinishing(context)) {
			this.dialog.show();
		}
	}
}
