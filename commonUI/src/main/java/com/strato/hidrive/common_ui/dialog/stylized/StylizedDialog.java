package com.strato.hidrive.common_ui.dialog.stylized;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListAdapter;

import com.strato.hidrive.common_ui.dialog.stylized.localized.LocalizedTextStrategy;
import com.strato.hidrive.domain.exception.TryCatchExceptionHandler;
import com.strato.hidrive.domain.optional.Optional;
import com.strato.hidrive.common_ui.dialog.stylized.listeners.NullOnStylizedDialogItemClickListener;
import com.strato.hidrive.common_ui.dialog.stylized.listeners.OnStylizedDialogButtonClickListener;
import com.strato.hidrive.common_ui.dialog.stylized.listeners.OnStylizedDialogItemClickListener;
import com.strato.hidrive.common_ui.dialog.stylized.view_factory.StylizedDialogViewFactory;
import com.strato.hidrive.domain.logger.LoggerUtil;

import java.util.List;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

public class StylizedDialog<V extends View & SavingStateView> {

	private static final float ACTIVE_BTN_ALPHA = 1f;
	private static final float INACTIVE_BTN_ALPHA = 0.5f;

	private AlertDialog alertDialog;
	private Context context;

	private final TryCatchExceptionHandler tryCatchExceptionHandler;

	private Optional<LocalizedTextStrategy> title = Optional.absent();
	private Optional<LocalizedTextStrategy> message = Optional.absent();

	private Optional<Typeface> typeface = Optional.absent();
	private Optional<Integer> buttonsTextColorRes = Optional.absent();

	private Optional<StylizedDialogViewFactory<V>> customViewFactory = Optional.absent();
	private Optional<V> customView = Optional.absent();

	private Optional<? extends List<? extends LocalizedTextStrategy>> customItems = Optional.absent();
	private Optional<? extends ListAdapter> customListAdapter = Optional.absent();

	private boolean cancelable = true;
	private boolean showKeyboard = false;

	private Optional<StylizedDialogButtonInfo> positiveButtonInfo = Optional.absent();
	private Optional<StylizedDialogButtonInfo> negativeButtonInfo = Optional.absent();

	private OnStylizedDialogItemClickListener dialogItemClickListener = NullOnStylizedDialogItemClickListener.INSTANCE;
	private DialogInterface.OnDismissListener onDismissListener = dialog -> {
	};
	private DialogInterface.OnCancelListener onCancelListener = dialog -> {
	};
	private DialogInterface.OnShowListener onShowListener = dialog -> {
	};

	private StylizedDialog(TryCatchExceptionHandler tryCatchExceptionHandler) {
		this.tryCatchExceptionHandler = tryCatchExceptionHandler;
	}

	public static <V extends View & SavingStateView> StylizedDialog<V>.Builder newBuilder(
			TryCatchExceptionHandler tryCatchExceptionHandler) {
		return new StylizedDialog<V>(tryCatchExceptionHandler).new Builder();
	}

	public class Builder {
		private Builder() {
		}

		public Builder setTitle(@NonNull LocalizedTextStrategy title) {
			StylizedDialog.this.title = Optional.of(title);
			return this;
		}

		public Builder setMessage(@NonNull LocalizedTextStrategy message) {
			StylizedDialog.this.message = Optional.of(message);
			return this;
		}

		public Builder setPositiveButton(@NonNull LocalizedTextStrategy positiveButtonTitle, boolean enabled, OnStylizedDialogButtonClickListener clickListener) {
			StylizedDialog.this.positiveButtonInfo = Optional.of(new StylizedDialogButtonInfo(positiveButtonTitle, enabled, clickListener));
			return this;
		}

		public Builder setPositiveButton(@NonNull LocalizedTextStrategy positiveButtonTitle, OnStylizedDialogButtonClickListener clickListener) {
			return setPositiveButton(positiveButtonTitle, true, clickListener);
		}

		public Builder setNegativeButton(@NonNull LocalizedTextStrategy negativeButtonTitle, OnStylizedDialogButtonClickListener clickListener) {
			StylizedDialog.this.negativeButtonInfo = Optional.of(new StylizedDialogButtonInfo(negativeButtonTitle, true, clickListener));
			return this;
		}

		public Builder setTypeface(@NonNull Typeface typeface) {
			StylizedDialog.this.typeface = Optional.of(typeface);
			return this;
		}

		public Builder setOnDismissListener(@NonNull DialogInterface.OnDismissListener onDismissListener) {
			StylizedDialog.this.onDismissListener = onDismissListener;
			return this;
		}

		public Builder setOnCancelListener(@NonNull DialogInterface.OnCancelListener onCancelListener) {
			StylizedDialog.this.onCancelListener = onCancelListener;
			return this;
		}

		public Builder setOnShowListener(@NonNull DialogInterface.OnShowListener onShowListener) {
			StylizedDialog.this.onShowListener = onShowListener;
			return this;
		}

		public Builder setCancelable(boolean cancelable) {
			StylizedDialog.this.cancelable = cancelable;
			return this;
		}

		public Builder setShowKeyboard(boolean showKeyboard) {
			StylizedDialog.this.showKeyboard = showKeyboard;
			return this;
		}

		public Builder setButtonsTextColor(@ColorRes int buttonsTextColor) {
			StylizedDialog.this.buttonsTextColorRes = Optional.of(buttonsTextColor);
			return this;
		}

		public Builder setItemClickListener(@NonNull OnStylizedDialogItemClickListener dialogItemClickListener) {
			StylizedDialog.this.dialogItemClickListener = dialogItemClickListener;
			return this;
		}

		public Builder setCustomView(@NonNull StylizedDialogViewFactory<V> viewFactory) {
			StylizedDialog.this.customViewFactory = Optional.of(viewFactory);
			return this;
		}

		public Builder setCustomItems(@NonNull List<? extends LocalizedTextStrategy> items) {
			StylizedDialog.this.customItems = Optional.of(items);
			return this;
		}

		public Builder setCustomListAdapter(@NonNull ListAdapter adapter) {
			StylizedDialog.this.customListAdapter = Optional.of(adapter);
			return this;
		}

		public StylizedDialog<V> build(@NonNull Context context) {
			StylizedDialog.this.context = context;
			StylizedDialog.this.alertDialog = createDialog();
			StylizedDialog.this.alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
				@Override
				public void onShow(DialogInterface dialog) {
					postModifyDialog((AlertDialog) dialog);
					StylizedDialog.this.onShowListener.onShow(dialog);
				}
			});
			return StylizedDialog.this;
		}

		private boolean showButtons() {
			return StylizedDialog.this.positiveButtonInfo.isPresent() || StylizedDialog.this.negativeButtonInfo.isPresent();
		}

		@NonNull
		private AlertDialog createDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(StylizedDialog.this.context);
			builder.setCancelable(StylizedDialog.this.cancelable);
			setTitleToBuilder(builder);
			setMessageToBuilder(builder);
			setButtonsToBuilder(builder);
			setCustomViewToBuilder(builder);
			setCustomTextItemsToBuilder(builder);
			setCustomListAdapterToBuilderToBuilder(builder);
			setOnDismissListenerToBuilder(builder);
			setOnCancelListenerToBuilder(builder);
			AlertDialog alertDialog = builder.create();
			setShowKeyBoardToDialog(alertDialog);
			return alertDialog;
		}

		private void setShowKeyBoardToDialog(AlertDialog alertDialog) {
			if (StylizedDialog.this.showKeyboard) {
				Window window = alertDialog.getWindow();
				if (window != null) {
					window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
				}
			}
		}

		private void setCustomListAdapterToBuilderToBuilder(AlertDialog.Builder builder) {
			if (StylizedDialog.this.customListAdapter.isPresent()) {
				builder.setAdapter(StylizedDialog.this.customListAdapter.get(), null);
			}
		}

		private void setCustomTextItemsToBuilder(AlertDialog.Builder builder) {
			if (StylizedDialog.this.customItems.isPresent()) {
				List<? extends LocalizedTextStrategy> localizedTextStrategies = StylizedDialog.this.customItems.get();
				String[] tmpArr = new String[localizedTextStrategies.size()];
				for (int i = 0; i < localizedTextStrategies.size(); i++) {
					tmpArr[i] = localizedTextStrategies.get(i).getLocalizedString();
				}
				builder.setItems(tmpArr, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						StylizedDialog.this.dialogItemClickListener.onClick(StylizedDialog.this, which);
					}
				});
			}
		}

		private void setCustomViewToBuilder(AlertDialog.Builder builder) {
			if (StylizedDialog.this.customViewFactory.isPresent()) {
				StylizedDialog.this.customView = Optional.of(StylizedDialog.this.customViewFactory.get().create());
				builder.setView(StylizedDialog.this.customView.get());
			}
		}

		private void setOnDismissListenerToBuilder(AlertDialog.Builder builder) {
			builder.setOnDismissListener(onDismissListener);
		}

		private void setOnCancelListenerToBuilder(AlertDialog.Builder builder) {
			builder.setOnCancelListener(onCancelListener);
		}

		private void setButtonsToBuilder(AlertDialog.Builder builder) {
			if (showButtons()) {
				String positiveButtonString = StylizedDialog.this.positiveButtonInfo.isPresent() ?
						StylizedDialog.this.positiveButtonInfo.get().title.getLocalizedString() : "";
				String negativeButtonString = StylizedDialog.this.negativeButtonInfo.isPresent() ?
						StylizedDialog.this.negativeButtonInfo.get().title.getLocalizedString() : "";
				builder.setPositiveButton(positiveButtonString, null)
						.setNegativeButton(negativeButtonString, null);
			}
		}

		private void setMessageToBuilder(AlertDialog.Builder builder) {
			if (StylizedDialog.this.message.isPresent()) {
				builder.setMessage(StylizedDialog.this.message.get().getLocalizedString());
			}
		}

		private void setTitleToBuilder(AlertDialog.Builder builder) {
			if (StylizedDialog.this.title.isPresent()) {
				builder.setTitle(StylizedDialog.this.title.get().getLocalizedString());
			}
		}

		private void postModifyDialog(AlertDialog alertDialog) {
			if (showButtons()) {
				Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

				configureButtonsTextColor(positiveButton, negativeButton);
				configureButtonsTextTypeface(positiveButton, negativeButton);
				configurePositiveButtonEnabling(positiveButton);
				configureNegativeButtonEnabling(negativeButton);
			}
		}

		private void configureNegativeButtonEnabling(Button negativeButton) {
			if (StylizedDialog.this.negativeButtonInfo.isPresent()) {
				negativeButton.setEnabled(StylizedDialog.this.negativeButtonInfo.get().enabled);
				negativeButton.setAlpha(StylizedDialog.this.negativeButtonInfo.get().enabled ? ACTIVE_BTN_ALPHA : INACTIVE_BTN_ALPHA);
				negativeButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						StylizedDialog.this.negativeButtonInfo.get().clickListener.onClick(StylizedDialog.this);
					}
				});
			}
		}

		private void configurePositiveButtonEnabling(Button positiveButton) {
			if (StylizedDialog.this.positiveButtonInfo.isPresent()) {
				positiveButton.setEnabled(StylizedDialog.this.positiveButtonInfo.get().enabled);
				positiveButton.setAlpha(StylizedDialog.this.positiveButtonInfo.get().enabled ? ACTIVE_BTN_ALPHA : INACTIVE_BTN_ALPHA);
				positiveButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						StylizedDialog.this.positiveButtonInfo.get().clickListener.onClick(StylizedDialog.this);
					}
				});
			}
		}

		private void configureButtonsTextTypeface(Button positiveButton, Button negativeButton) {
			if (StylizedDialog.this.typeface.isPresent()) {
				positiveButton.setTypeface(StylizedDialog.this.typeface.get());
				negativeButton.setTypeface(StylizedDialog.this.typeface.get());
			}
		}

		private void configureButtonsTextColor(Button positiveButton, Button negativeButton) {
			if (StylizedDialog.this.buttonsTextColorRes.isPresent()) {
				int textColor = ContextCompat.getColor(
						StylizedDialog.this.context,
						StylizedDialog.this.buttonsTextColorRes.get());
				positiveButton.setTextColor(textColor);
				negativeButton.setTextColor(textColor);
			}
		}
	}

	public final void show() {
		if (!alertDialog.isShowing()) {
			tryCatchExceptionHandler.handle(() -> alertDialog.show());
		} else {
			LoggerUtil.logD(getClass().getSimpleName(), "Can't show override file dialog");
		}
//		registerMainNavigationViewContainerListener();
	}

	public final void dismiss() {
		this.alertDialog.dismiss();
//		unregisterActivityLifecycleListener();
		unregisterMainNavigationViewContainerListener();
	}

	public final void setPositiveButtonEnabled(boolean enabled) {
		if (StylizedDialog.this.positiveButtonInfo.isPresent()) {
			StylizedDialog.this.positiveButtonInfo =
					Optional.of(
							new StylizedDialogButtonInfo(
									StylizedDialog.this.positiveButtonInfo.get().title,
									enabled,
									StylizedDialog.this.positiveButtonInfo.get().clickListener));
			Button button = this.alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
			button.setEnabled(enabled);
			button.setAlpha(enabled ? ACTIVE_BTN_ALPHA : INACTIVE_BTN_ALPHA);
		} else {
			throw new RuntimeException("You must specify positive button first");
		}
	}

	public boolean hasCustomView(){
		return this.customView.isPresent();
	}

	public V getCustomView() {
		if (this.customView.isPresent()) {
			return this.customView.get();
		} else {
			throw new RuntimeException("Custom view wasn't setted");
		}
	}


    //TODO alk
	private void registerMainNavigationViewContainerListener() {
//		if (this.context instanceof MainNavigationViewContainer) {
//			((MainNavigationViewContainer) this.context).addMainNavigationViewContainerListener(this.mainNavigationViewContainerListener);
//		}
	}
    //TODO alk
	private void unregisterMainNavigationViewContainerListener() {
//		if (this.context instanceof MainNavigationViewContainer) {
//			((MainNavigationViewContainer) this.context).removeMainNavigationViewContainerListener(this.mainNavigationViewContainerListener);
//		}
	}

	private void handleConfigChanges() {
		if (this.alertDialog != null && this.alertDialog.isShowing()) {
			this.alertDialog.dismiss();

			if (this.customView.isPresent()) {
				Bundle savedState = this.customView.get().createSavedState();
				ViewParent customViewParent = this.customView.get().getParent();
				if (customViewParent instanceof ViewGroup) {
					((ViewGroup) customViewParent).removeView(this.customView.get());
				}
				recreateBuilderAndShowDialog();
				this.customView.get().restoreSavedState(savedState);
			} else {
				recreateBuilderAndShowDialog();
			}
		}
	}

	private void recreateBuilderAndShowDialog() {
		new Builder()
				.build(this.context)
				.show();
	}

}
