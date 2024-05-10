package com.strato.hidrive.common_ui.dialog.stylized.listeners;

import com.strato.hidrive.common_ui.dialog.stylized.StylizedDialog;

/**
 * Created by Sergey Shandyuk on 12/21/2016.
 */

public class NullOnStylizedDialogButtonClickListener implements OnStylizedDialogButtonClickListener {
	public static final OnStylizedDialogButtonClickListener INSTANCE = new NullOnStylizedDialogButtonClickListener();

	private NullOnStylizedDialogButtonClickListener() {
	}

	@Override
	public void onClick(StylizedDialog dialog) {

	}
}
