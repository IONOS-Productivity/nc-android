package com.strato.hidrive.common_ui.activity.request_codes;

/**
 * User: zuzik
 * Date: 6/16/16
 */
public class ActivityRequestCode {

	public static class RemoteChooserActivity {
		public static final int MOVE_CHOOSER_REQUEST_CODE = 106;
		public static final int COPY_CHOOSER_REQUEST_CODE = 107;
	}

	public static class MainActivity {
		public static final int PICK_FILES_FOR_UPLOAD_WITH_EXTERNAL_FILES_PICKER = 2001;
		public static final int TAKE_PHOTO_FROM_CAMERA = 2002;
		public static final int PLAY_SERVICE_AVAILABILITY = 2003;
		public static final int PICK_FOLDER_FOR_AUTO_UPLOAD = 2004;
		public static final int DISABLE_HIBERNATION = 2005;
		public static final int IN_APP_UPDATE = 2006;
		public static final int OPEN_UPLOAD_SCREEN_REQUEST_CODE = 2154;
	}

	public static class SearchActivity {
		public static final int OPEN_UPLOAD_SCREEN_RESULT_CODE = 2155;
	}

	public static class SettingsView {
		public static final int CHANGE_TARGET_UPLOAD_FOLDER = 5010;
	}

	public static class HidriveReceiverActivity {
		public static final int IMPORT_REQUEST = 5001;
		public static final int CHOOSE_FOLDER_REQUEST = 5002;
		public static final int DATA_PROTECTION_REQUEST = 5003;
	}

	public static class PinCodeActivity {
		public static final int REQUEST_PIN_CODE_CREATE = 7001;
		public static final int REQUEST_PIN_CODE_EDIT = 7002;
	}

	public static final int LOGIN_REQUEST = 6000;

	public static class PictureViewer {
		public static final int PICK_DIRECTORY_WITH_SYSTEM_FILES_PICKER = 8001;

	}

	public static final int REQUEST_QR_SCANNER = 9000;

}
