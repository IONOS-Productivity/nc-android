package com.ionos.common_ui.dialog.wrapper;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.ionos.common_ui.dialog.stylized.SavingStateView;
import com.ionos.common_ui.dialog.stylized.localized.LocalizedTextStrategy;
import com.ionos.common_ui.R;

public class StylizedApplyToAllDialogView extends ConstraintLayout
		implements SavingStateView {
	private final String CHECKBOX_STATE_KEY = "CHECKBOX_STATE_KEY";

	private CheckBox checkBox;

	public StylizedApplyToAllDialogView(Context context, LocalizedTextStrategy text, LocalizedTextStrategy hint) {
		this(context, null, text, hint, null);
	}

	public StylizedApplyToAllDialogView(Context context, LocalizedTextStrategy text, LocalizedTextStrategy hint, @Nullable LocalizedTextStrategy description) {
		this(context, null, text, hint, description);
	}

	public StylizedApplyToAllDialogView(Context context, AttributeSet attrs, LocalizedTextStrategy text, LocalizedTextStrategy hint, @Nullable LocalizedTextStrategy description) {
		super(context, attrs);
		View.inflate(context, R.layout.view_overwrite_dialog_content, this);
		configure(text, hint, description);
	}

	private void configure(LocalizedTextStrategy text, LocalizedTextStrategy hint, @Nullable LocalizedTextStrategy description) {
		this.checkBox = findViewById(R.id.applyToAllCheckBox);
	}

	public final void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
		this.checkBox.setOnCheckedChangeListener(listener);
	}

	public final boolean getCheckboxState() {
		return checkBox.isChecked();
	}

	public final void setCheckboxState(boolean newState) {
		checkBox.setChecked(newState);
	}

	@Override
	public Bundle createSavedState() {
		Bundle bundle = new Bundle();
		bundle.putBoolean(CHECKBOX_STATE_KEY, getCheckboxState());
		return bundle;
	}

	@Override
	public void restoreSavedState(Bundle bundle) {
		if (bundle.containsKey(CHECKBOX_STATE_KEY)) {
			boolean savedCheckboxState = bundle.getBoolean(CHECKBOX_STATE_KEY, false);
			setCheckboxState(savedCheckboxState);
		}
	}

}