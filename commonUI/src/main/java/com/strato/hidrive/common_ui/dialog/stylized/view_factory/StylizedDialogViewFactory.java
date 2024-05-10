package com.strato.hidrive.common_ui.dialog.stylized.view_factory;

import android.view.View;

import com.strato.hidrive.common_ui.dialog.stylized.SavingStateView;

/**
 * Created by Sergey Shandyuk on 12/29/2016.
 */

public interface StylizedDialogViewFactory<V extends View & SavingStateView> {
	V create();
}
