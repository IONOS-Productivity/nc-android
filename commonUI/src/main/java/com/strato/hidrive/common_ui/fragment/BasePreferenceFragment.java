package com.strato.hidrive.common_ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.XmlRes;
import androidx.preference.PreferenceFragmentCompat;

import com.strato.hidrive.common_ui.R;

public abstract class BasePreferenceFragment extends PreferenceFragmentCompat {
	private Context wrappedContext;

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		this.wrappedContext = new ContextThemeWrapper(context, R.style.Theme_App_NoTitleBar);
	}

	@Override
	public void onDetach() {
		super.onDetach();
		this.wrappedContext = null;
	}

	@Nullable
	@Override
	public Context getContext() {
		return this.wrappedContext;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO: this style shouldn't be applied here
		//  the activity theme should declare proper colors for all controls and dialogs instead
		requireContext().getTheme().applyStyle(R.style.PreferenceGeneralStyle, true);
		requireActivity().getTheme().applyStyle(R.style.PreferenceGeneralStyle, true);
	}

	@Override
	public void onCreatePreferences(Bundle bundle, String rootKey) {
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		setPreferencesFromResource(getPreferencesResId(), null);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@XmlRes
	protected abstract int getPreferencesResId();
}
