package com.strato.hidrive.views.exif_info;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.strato.hidrive.views.exif_info.observable_scroll.ObservableScrollView;
import com.strato.hidrive.views.exif_info.tracker.ExifInfoEventTracker;
import com.strato.hidrive.views.exif_info.transformation.ExifInfoTransformations;
import com.strato.hidrive.views.exif_info.utils.ScreenConfiguration;
import com.strato.hidrive.views.exif_info.utils.ScreenUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sergey Shandyuk on 4/1/2016.
 */
public class ExifInfoView extends NavigationView implements ObservableScrollView.ObservableScrollViewCallbacks {
	private static final float FILE_TYPE_ICON_START_ALPHA_VALUE = 0.5f;
	private static final float FILE_TYPE_ICON_MAX_ALPHA_VALUE = 1f;
	private static final float OVERLAY_CONTAINER_ALPHA_MAX_VALUE = 0.3f;
	private static final float SLOW_MOTION_COEFFICIENT = 2f;
	private static final float TITLE_TRANSLATION_COEFFICIENT = 4.5f;

	private View overlayContainerView;
	private View titleContainer;
	private FrameLayout headerContainer;
	private ImageView headerImage;
	private ImageView fileTypeIcon;
	private LinearLayout itemsContainer;
	private View closeBtn;
	private TextView fileNameTextView;
	private int flexibleRange;
	private int minOverlayTransitionY;
	private int actionBarSize;
	private int flexibleSpaceImageHeight;
	private int panelWidth;
	private Disposable loadFileBitmapDisposable = Disposables.disposed();
	private Disposable loadMetaInfoDisposable = Disposables.disposed();

	private ExifInfoTransformations transformations = new ExifInfoTransformations();
	@Nullable
	private ExifInfoListener exifInfoListener;
	@Nullable
	private ExifInfoEventTracker tracker;
	private ExifInfoShowError exifInfoShowError = (__, ___)->{};

	public ExifInfoView(Context context) {
		this(context, null);
	}

	public ExifInfoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ExifInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	@Override
	protected void onDetachedFromWindow() {
		this.loadFileBitmapDisposable.dispose();
		this.loadMetaInfoDisposable.dispose();

		super.onDetachedFromWindow();
	}

	private void setContentView(int layoutRes) {
		LayoutInflater.from(getContext()).inflate(layoutRes, this, true);
	}

	private void init(AttributeSet attrs) {
		setContentView(R.layout.view_exif_info);
		readAttrs(attrs);
		calcScrollRange();
		findViews();
		getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
		ViewCompat.setOnApplyWindowInsetsListener(this, this::applyInsets);
	}

	private void readAttrs(AttributeSet attrs) {
		TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ExifInfoView, 0, 0);

		try {
			this.panelWidth = typedArray.getDimensionPixelSize(R.styleable.ExifInfoView_panel_width, -1);
			this.flexibleSpaceImageHeight = typedArray.getDimensionPixelSize(R.styleable.ExifInfoView_panel_header_image_height,
					getResources().getDimensionPixelSize(R.dimen.exif_info_header_image_height));
			this.actionBarSize = typedArray.getDimensionPixelSize(R.styleable.ExifInfoView_panel_toolbar_height,
					getResources().getDimensionPixelSize(R.dimen.exif_info_toolbar_height));
		} finally {
			typedArray.recycle();
		}
	}

	private void configurePanelWidth() {
		DrawerLayout.LayoutParams layoutParams;
		ScreenConfiguration screenConfiguration = new ScreenConfiguration();
		if (screenConfiguration.large(getContext()) || screenConfiguration.landscape(getContext())) {
			layoutParams = new DrawerLayout.LayoutParams(this.panelWidth, ViewGroup.LayoutParams.MATCH_PARENT);
		} else {
			layoutParams = new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.exif_info_panel_margin_left), 0, 0, 0);
		}

		layoutParams.gravity = GravityCompat.END;
		setLayoutParams(layoutParams);
	}

	private void configurePanelWidthOnRotation() {
		DrawerLayout.LayoutParams layoutParams = (DrawerLayout.LayoutParams) getLayoutParams();
		ScreenConfiguration screenConfiguration = new ScreenConfiguration();
		if (screenConfiguration.large(getContext()) || screenConfiguration.landscape(getContext())) {
			layoutParams.width = this.panelWidth;
		} else {
			layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
			layoutParams.setMargins(getResources().getDimensionPixelSize(R.dimen.exif_info_panel_margin_left), 0, 0, 0);
		}
		setLayoutParams(layoutParams);
	}

	private void findViews() {
		this.fileNameTextView = findViewById(R.id.file_name_tv);
		this.itemsContainer = findViewById(R.id.items_container);
		this.headerContainer = findViewById(R.id.header_container);
		this.headerImage = findViewById(R.id.header_image);
		this.fileTypeIcon = findViewById(R.id.file_type_icon);
		this.closeBtn = findViewById(R.id.close_panel_btn);
		this.titleContainer = findViewById(R.id.title_container);
		this.overlayContainerView = findViewById(R.id.overlay_container);
		this.closeBtn.setOnClickListener(this.onCloseClickListener);
		ObservableScrollView scrollView = findViewById(R.id.scroll_view);
		scrollView.setScrollViewCallbacks(this);
	}

	@NonNull
	private WindowInsetsCompat applyInsets(View view, WindowInsetsCompat windowInsets) {
		Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
		MarginLayoutParams params = (MarginLayoutParams) closeBtn.getLayoutParams();
		params.topMargin = getResources().getDimensionPixelSize(R.dimen.exif_info_close_btn_margin_top) + insets.top;
		params.leftMargin = getResources().getDimensionPixelSize(R.dimen.exif_info_close_btn_margin_left) + insets.left;
		closeBtn.setLayoutParams(params);
		return WindowInsetsCompat.CONSUMED;
	}

	private void calcScrollRange() {
		this.flexibleRange = this.flexibleSpaceImageHeight - this.actionBarSize;
		this.minOverlayTransitionY = this.actionBarSize - this.flexibleSpaceImageHeight;
	}

	public void setExifInfoListener(@Nullable ExifInfoListener exifInfoListener) {
		this.exifInfoListener = exifInfoListener;
	}

	public void setTracker(@Nullable ExifInfoEventTracker tracker){
		this.tracker = tracker;
	}

	public void setExifInfoShowError(@NonNull ExifInfoShowError showError){
		this.exifInfoShowError = showError;
	}

	public void setTransformations(@NonNull ExifInfoTransformations transformations){
		this.transformations = transformations;
	}

	@Override
	public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
		setElementsTranslationY(scrollY);
		setElementsAlpha(scrollY);
	}

	private void setElementsTranslationY(int scrollY) {
		setViewTranslationY(this.overlayContainerView, -scrollY, this.minOverlayTransitionY);
		setViewTranslationY(this.headerContainer, -scrollY / SLOW_MOTION_COEFFICIENT, this.minOverlayTransitionY);
		setViewTranslationY(this.titleContainer, -scrollY, this.minOverlayTransitionY + closeBtn.getTop() / TITLE_TRANSLATION_COEFFICIENT);
	}

	private void setElementsAlpha(int scrollY) {
		float currentValue = (float) scrollY / this.flexibleRange;
		this.overlayContainerView.setAlpha(getViewAlpha(currentValue, OVERLAY_CONTAINER_ALPHA_MAX_VALUE));
		this.fileTypeIcon.setAlpha(FILE_TYPE_ICON_START_ALPHA_VALUE - getViewAlpha(currentValue, FILE_TYPE_ICON_MAX_ALPHA_VALUE));
	}

	private void setViewTranslationY(View view, float value, float minValue) {
		view.setTranslationY(Math.max(value, minValue));
	}

	private float getViewAlpha(float value, float maxValue) {
		return Math.min(value, maxValue);
	}

	public void setHeaderImage(Bitmap image, ImageView.ScaleType scaleType) {
		this.headerImage.setImageBitmap(image);
		this.headerImage.setScaleType(scaleType);
	}

	public void setTitle(String title) {
		this.fileNameTextView.setText(title);
	}

	public void setFileTypeIcon(@DrawableRes int icon) {
		this.fileTypeIcon.setImageResource(icon);
	}

	public void addItems(ExifMetaData result, ExifInfoFile fileInfo) {
		List<ExifInfoItemView> infoItemList = convertMetaDataToInfoItem(result, fileInfo);
		for (ExifInfoItemView infoItem : infoItemList) {
			this.itemsContainer.addView(infoItem);
		}
	}

	public void clearItems() {
		this.itemsContainer.removeAllViews();
	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		configurePanelWidthOnRotation();
	}

	private final OnClickListener onCloseClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (tracker != null) {
				tracker.trackCancel(getContext());
			}
			if (exifInfoListener != null) {
				exifInfoListener.onCloseClicked();
			}
		}
	};

	private final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
		@Override
		public void onGlobalLayout() {
			getViewTreeObserver().removeOnGlobalLayoutListener(this);
			configurePanelWidth();
		}
	};

	public void loadMetaInfo(ExifInfoProvider exifInfoProvider) {
		clearItems();
		if (isAttachedToWindow()) {
			var exifInfoFile = exifInfoProvider.getExifInfoFile();
			setTitle(exifInfoFile.getFileName());
			setFileTypeIcon(exifInfoFile.getIconResource());
			setSizedFileIcon(exifInfoFile.getIconResource());

			loadMetaInfo(exifInfoProvider.getExifInfoLoadMetaData(), exifInfoProvider.getExifInfoFile());
			loadMetaInfoHeaderImage(exifInfoProvider.getExifInfoLoadHeader());
		}
	}

	private void loadMetaInfo(ExifInfoLoadMetaData exifInfoLoadMetaData, ExifInfoFile exifInfoFile) {
		loadMetaInfoDisposable.dispose();
		loadMetaInfoDisposable = exifInfoLoadMetaData.invoke()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						result -> addItems(result, exifInfoFile),
						error -> exifInfoShowError.invoke(getContext(), error.getMessage())
				);
	}

	private List<ExifInfoItemView> convertMetaDataToInfoItem(
			ExifMetaData exifMetaData,
			ExifInfoFile fileInfo
	) {
		List<ExifInfoItemView> exifInfoItemViews = new ArrayList<>();

		if (exifMetaData != null) {
			if (exifMetaData.getPath() != null) {
				exifInfoItemViews.add(
						new ExifInfoItemView(
								getContext(),
								R.drawable.ic_exif_info_folder_black_18dp,
								transformations.getPathToTitleTransformation().transform(exifMetaData.getPath())
						)
				);
			}
			exifInfoItemViews.add(
					getSizeItem(
							exifMetaData.getSize() != null ?
									exifMetaData.getSize() :
									fileInfo.getContentLength())
			);
			if (containsImageSize(exifMetaData)) {
				exifInfoItemViews.add(new ExifInfoItemView(getContext(), R.drawable.ic_exif_info_crop_black_18dp,
						exifMetaData.getImageWidth() + " x " + exifMetaData.getImageHeight()));
			}
			if (exifMetaData.getMtime() > 0) {
				exifInfoItemViews.addAll(getDateAndTimeItems(exifMetaData.getMtime()));
			} else if (fileInfo.getLastModified() > 0) {
				exifInfoItemViews.addAll(getDateAndTimeItems(fileInfo.getLastModified()));
			}

			if (exifMetaData.getModel() != null && !exifMetaData.getModel().isEmpty()) {
				String model = exifMetaData.getModel();

				if (exifMetaData.getMake() != null && !model.contains(exifMetaData.getMake())) {
					model = exifMetaData.getMake() + " " + model;
				}
				exifInfoItemViews.add(new ExifInfoItemView(getContext(), R.drawable.ic_exif_info_photo_camera_black_18dp, model));
			}

			if (exifMetaData.getAperture() != -1d) {
				exifInfoItemViews.add(new ExifInfoItemView(getContext(), R.drawable.ic_exif_info_camera_black_18dp,
						String.format("f/%s", new DecimalFormat("#.##").format(exifMetaData.getAperture()))));
			}
			if (exifMetaData.getExposureTime() != null && !exifMetaData.getExposureTime().isEmpty() && !"undef".equals(exifMetaData.getExposureTime())) {
				exifInfoItemViews.add(new ExifInfoItemView(getContext(), R.drawable.ic_exif_info_exposure_black_18dp, exifMetaData.getExposureTime() + "s"));
			}
			if (exifMetaData.getISO() != -1) {
				exifInfoItemViews.add(new ExifInfoItemView(getContext(), R.drawable.ic_exif_info_iso_black_18dp, exifMetaData.getISO() + ""));
			}
			if (exifMetaData.getFocalLength() != -1d) {
				exifInfoItemViews.add(new ExifInfoItemView(getContext(), R.drawable.ic_exif_info_photo_size_select_actual_black_18dp,
						(new DecimalFormat("#.##").format(exifMetaData.getFocalLength()) + "mm")));
			}
			if (exifMetaData.getLocationText() != null) {
				exifInfoItemViews.add(new ExifInfoItemView(getContext(), R.drawable.ic_exif_info_location_on_black_18dp, exifMetaData.getLocationText()));
			}
		}
		return exifInfoItemViews;
	}

	private List<ExifInfoItemView> getDateAndTimeItems(long time) {
		ArrayList items = new ArrayList();
		items.add(
				new ExifInfoItemView(
						getContext(),
						R.drawable.ic_exif_info_event_black_18dp,
						transformations.getMillisToDateDescriptionTransformation().transform(time)
				)
		);
		items.add(
				new ExifInfoItemView(
						getContext(),
						R.drawable.ic_exif_info_access_time_black_18dp,
						transformations.getMillisToTimeDescriptionTransformation().transform(time)
				)
		);
		return items;
	}

	private ExifInfoItemView getSizeItem(long size) {
		return new ExifInfoItemView(
				getContext(),
				R.drawable.ic_exif_info_save_black_18dp,
				transformations.getBytesToStringSizeTransformation().transform(size)
		);
	}

	private boolean containsImageSize(ExifMetaData exifMetaData) {
		return exifMetaData.getImageHeight() != null && exifMetaData.getImageWidth() != null
				&& exifMetaData.getImageHeight() >= 0 && exifMetaData.getImageWidth() >= 0;
	}

	private void setSizedFileIcon(@DrawableRes int iconResource) {
		int fileTypeIconSize = getResources().getDimensionPixelOffset(R.dimen.exif_info_detail_info_mimetype_icon_size);
		setHeaderImage(
				getBitmapFromVectorDrawable(
						iconResource,
						fileTypeIconSize,
						fileTypeIconSize),
				ImageView.ScaleType.CENTER
		);
	}

	private Bitmap getBitmapFromVectorDrawable(int drawableId, int width, int height) {
		Drawable drawable = ContextCompat.getDrawable(getContext(), drawableId);
		Bitmap bitmap = Bitmap.createBitmap(
				width,
				height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	private void loadMetaInfoHeaderImage(ExifInfoLoadHeader exifInfoLoadHeader) {
		this.loadFileBitmapDisposable.dispose();

		int width = calcPanelWidth();
		int height = (int) getResources().getDimension(R.dimen.exif_info_header_image_height);
		this.loadFileBitmapDisposable = exifInfoLoadHeader.invoke(headerImage, width, height)
				.subscribe(
						() -> {},
						t->{}
				);
	}

	private int calcPanelWidth() {
		ScreenConfiguration screenConfiguration = new ScreenConfiguration();
		if (screenConfiguration.large(getContext())) {
			return (int) getResources().getDimension(R.dimen.exif_info_panel_width);
		} else {
			int portraitWidth = Math.min(ScreenUtils.getDisplayWidth(getContext()), ScreenUtils.getDisplayHeight(getContext()));
			int landscapeWidth = (int) getResources().getDimension(R.dimen.exif_info_panel_width);
			return Math.max(portraitWidth, landscapeWidth);
		}
	}

	public void onFileChanged() {
		this.loadFileBitmapDisposable.dispose();
		this.loadMetaInfoDisposable.dispose();
	}
}
