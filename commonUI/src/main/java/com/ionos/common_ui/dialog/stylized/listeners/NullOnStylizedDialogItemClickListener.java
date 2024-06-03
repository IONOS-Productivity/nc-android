package com.ionos.common_ui.dialog.stylized.listeners;

import com.ionos.common_ui.dialog.stylized.StylizedDialog;

/**
 * Created by Sergey Shandyuk on 12/29/2016.
 */

public class NullOnStylizedDialogItemClickListener implements OnStylizedDialogItemClickListener {
	public static final OnStylizedDialogItemClickListener INSTANCE = new NullOnStylizedDialogItemClickListener();

	private NullOnStylizedDialogItemClickListener() {
	}

	@Override
	public void onClick(StylizedDialog dialog, int which) {

	}
}
