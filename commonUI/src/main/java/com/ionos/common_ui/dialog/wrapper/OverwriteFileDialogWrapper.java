package com.ionos.common_ui.dialog.wrapper;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.ionos.common_ui.dialog.stylized.localized.ArgsLocalizedTextStrategy;
import com.ionos.common_ui.dialog.stylized.localized.StringLocalizedStrategy;
import com.ionos.common_ui.dialog.stylized.localized.StringResLocalizedStrategy;
import com.strato.hidrive.common_ui.R;
import com.ionos.common_ui.di.CommonUiComponent;
import com.ionos.common_ui.dialog.stylized.StylizedDialog;
import com.ionos.common_ui.dialog.stylized.overwrite_file_dialog.OverwriteCancelListener;
//import com.ionos.stylized.common_ui.ICustomFonts;
import com.ionos.domain.exception.TryCatchExceptionHandler;

import javax.inject.Inject;

public class OverwriteFileDialogWrapper {
	private final StylizedDialog<StylizedApplyToAllDialogView> dialog;
	private final OverwriteCancelListener listener;
    //TODO alk

    //	@Inject
//	ICustomFonts customFonts;
	@Inject
	TryCatchExceptionHandler tryCatchExceptionHandler;
//	@Inject
//	OverwriteFileDialogEventTracker tracker;

	public OverwriteFileDialogWrapper(@NonNull Context context,
									  @NonNull String fileName,
									  @NonNull OverwriteCancelListener dialogClickListener,
									  boolean lastInQueue) {
		CommonUiComponent.from(context).inject(this);
		this.listener = dialogClickListener;
		this.dialog = createDialog(context, fileName, lastInQueue);
	}

	private StylizedDialog<StylizedApplyToAllDialogView> createDialog(@NonNull Context context,
																	  @NonNull String fileName,
																	  boolean lastInQueue) {

		StylizedDialog<StylizedApplyToAllDialogView>.Builder builder = StylizedDialog.newBuilder(this.tryCatchExceptionHandler);

		builder.setMessage(new ArgsLocalizedTextStrategy(context, R.string.this_item_exists, R.string.file, fileName))
				.setPositiveButton(
						new StringResLocalizedStrategy(context, R.string.override),
						dialog -> {
//							this.tracker.trackOverwriteClicked();
							boolean applyToAll = isApplyToAll(dialog);
							listener.onOverwriteClicked(applyToAll);
							dialog.dismiss();
						})
				.setNegativeButton(
						new StringResLocalizedStrategy(context, R.string.cancel_btn_title),
						dialog -> {
//							this.tracker.trackCancelClicked();
							boolean applyToAll = isApplyToAll(dialog);
							listener.onCancelClicked(applyToAll);
							dialog.dismiss();
						})
//				.setOnShowListener(__ -> this.tracker.trackPage())
				.setButtonsTextColor(R.color.stylized_edit_text_line_color)
//				.setTypeface(this.customFonts.getMainRegularFont())
				.setCancelable(false);

		if (!lastInQueue) {
			builder.setCustomView(() -> createDialogView(context));
		}

		return builder.build(context);
	}

	private boolean isApplyToAll(@NonNull StylizedDialog<?> dialog) {
		if (!dialog.hasCustomView()) return false;

		boolean applyToAll = false;
		View dialogContent = dialog.getCustomView();
		if (dialogContent instanceof StylizedApplyToAllDialogView) {
			applyToAll = ((StylizedApplyToAllDialogView) dialogContent).getCheckboxState();
		}
		return applyToAll;
	}

	@NonNull
	private StylizedApplyToAllDialogView createDialogView(@NonNull Context context) {
		StylizedApplyToAllDialogView view = new StylizedApplyToAllDialogView(
				context,
				new StringLocalizedStrategy(""),
				new StringResLocalizedStrategy(context, 0));
		view.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            this.tracker.trackApplyToAllCheckChanged(isChecked);
        });
		return view;
	}

	@NonNull
	public final StylizedDialog<StylizedApplyToAllDialogView> show() {
		this.dialog.show();
		return dialog;
	}

	@NonNull
	public OverwriteCancelListener getListener() {
		return listener;
	}
}
