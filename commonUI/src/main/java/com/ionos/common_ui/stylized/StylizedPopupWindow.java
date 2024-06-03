package com.ionos.common_ui.stylized;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.PopupWindow;

public class StylizedPopupWindow extends PopupWindow {

	public StylizedPopupWindow() {
		configure();
	}

	public StylizedPopupWindow(Context context) {
		super(context);
		configure();
	}

	public StylizedPopupWindow(View contentView) {
		super(contentView);
		configure();
	}

	public StylizedPopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		configure();
	}

	public StylizedPopupWindow(int width, int height) {
		super(width, height);
		configure();
	}

	public StylizedPopupWindow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		configure();
	}

	public StylizedPopupWindow(View contentView, int width, int height) {
		super(contentView, width, height);
		configure();
	}

	public StylizedPopupWindow(View contentView, int width, int height, boolean focusable) {
		super(contentView, width, height, focusable);
		configure();
	}
	
	private void configure() {
		setBackgroundDrawable(new BitmapDrawable());
		setTouchable(true);
		setOutsideTouchable(false);
		getContentView().setFocusableInTouchMode(true);
		getContentView().setOnKeyListener(new View.OnKeyListener() {        
		    @Override
		    public boolean onKey(View v, int keyCode, KeyEvent event) {
		        if (keyCode ==  KeyEvent.KEYCODE_MENU && 
		                event.getRepeatCount() == 0 && 
		                event.getAction() == KeyEvent.ACTION_DOWN) {
		        	dismiss();
		            return true;
		        }                
		        return false;
		    }
		});
	}

}
