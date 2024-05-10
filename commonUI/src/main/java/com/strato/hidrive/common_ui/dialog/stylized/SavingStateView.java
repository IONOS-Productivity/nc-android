package com.strato.hidrive.common_ui.dialog.stylized;

import android.os.Bundle;

/**
 * Created by Sergey Shandyuk on 1/19/2017.
 */

public interface SavingStateView {
	Bundle createSavedState();

	void restoreSavedState(Bundle bundle);
}
