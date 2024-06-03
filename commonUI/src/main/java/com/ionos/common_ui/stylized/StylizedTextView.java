package com.ionos.common_ui.stylized;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.strato.hidrive.common_ui.R;
import com.strato.hidrive.domain.logger.LoggerUtil;

public class StylizedTextView extends AppCompatTextView {
	private static final int NORMAL = 0;
	private static final int BOLD = 1;
    //TODO alk

//	private static ICustomFonts customFontsInstance;

	public static void initialize(ICustomFonts instance) {
//		if (customFontsInstance == null) {
//			customFontsInstance = instance;
//		}
	}

	public StylizedTextView(Context context) {
		super(context);
		if (isInEditMode()) {
			return;
		}
		checkInstanceNotNull();
//		setTypeface(customFontsInstance.getRegularTypeface());
	}

	public StylizedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (isInEditMode()) {
			return;
		}
		checkInstanceNotNull();
		initialize(attrs);
	}

	public StylizedTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	private void initialize(AttributeSet attrs) {
		if (!isInEditMode()) {
			int textStyle = attrs.getAttributeIntValue("http://schemas.android.com/apk/res/android", "textStyle", StylizedTextView.NORMAL);

//			if (textStyle == StylizedTextView.BOLD) {
//				setTypeface(customFontsInstance.getMainBoldFont());
//			} else {
//				setTypeface(customFontsInstance.getMainRegularFont());
//			}
			readCustomAttributes(attrs);
		} else {
			setText(this.getClass().getSimpleName());
		}
	}

	private void readCustomAttributes(AttributeSet attrs) {
		TypedArray types = getContext().obtainStyledAttributes(attrs, R.styleable.StylizedTextView);

		final int attributesCount = types.getIndexCount();
		for (int i = 0; i < attributesCount; ++i) {
			int attr = types.getIndex(i);

			if (attr == R.styleable.StylizedTextView_typeface) {
				applyTypeface(types.getString(attr));
			} else {
				LoggerUtil.logW(getClass().getSimpleName(), "Found unknown style");
			}
		}
		types.recycle();
	}

	public void applyTypeface(String typeface) {
//		if (typeface.equalsIgnoreCase("light")) {
//			setTypeface(customFontsInstance.getLightTypeface());
//		} else if (typeface.equalsIgnoreCase("medium")) {
//			setTypeface(customFontsInstance.getMediumTypeface());
//		} else if (typeface.equalsIgnoreCase("bold")) {
//			setTypeface(customFontsInstance.getMainBoldFont());
//		} else {
//			setTypeface(customFontsInstance.getRegularTypeface());
//		}
	}

	private void checkInstanceNotNull() {
//		if (!isInEditMode() && customFontsInstance == null) {
//			throw new NullPointerException("CustomFontsInstance == null \n Must call \"initialize\"");
//		}
	}
}

