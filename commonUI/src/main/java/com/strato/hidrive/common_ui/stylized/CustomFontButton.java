package com.strato.hidrive.common_ui.stylized;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CustomFontButton extends AppCompatButton {
    //TODO alk

//	private static ICustomFonts customFontsInstance;

	public static void initialize(ICustomFonts instance) {
//		if (customFontsInstance == null) {
//			customFontsInstance = instance;
//		}
	}

	public CustomFontButton(Context context) {
		super(context);
		initialize();
	}

	public CustomFontButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	public CustomFontButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	private void initialize() {
		if (isInEditMode()) {
			return;
		}
		checkInstanceNotNull();
//		setTypeface(customFontsInstance.getMainRegularFont());
	}

	private static void checkInstanceNotNull() {
//		if (customFontsInstance == null) {
//			throw new NullPointerException("CustomFontsInstance == null \n Must call \"initialize\"");
//		}
	}
}
