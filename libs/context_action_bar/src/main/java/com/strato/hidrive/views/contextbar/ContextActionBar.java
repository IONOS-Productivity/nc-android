package com.strato.hidrive.views.contextbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.strato.hidrive.stylized_view.StylizedPopupWindow;
import com.strato.hidrive.views.contextbar.di.ContextbarComponent;
import com.strato.hidrive.views.contextbar.strategy.configuration.ICABConfigurationStrategy;
import com.strato.hidrive.views.contextbar.strategy.configuration.ToolbarItemClickListener;
import com.strato.hidrive.views.contextbar.strategy.mode.ICABModeStrategy;
import com.strato.hidrive.views.contextbar.strategy.popup_header.PopupHeaderBundle;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItem;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItemBundle;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItemPosition;
import com.strato.hidrive.views.contextbar.toolbar.ToolbarItemType;
import com.strato.hidrive.views.contextbar.toolbar.views.ResourceBundle;
import com.strato.hidrive.views.contextbar.toolbar.views.ToolbarItemView;
import com.strato.hidrive.views.contextbar.toolbar.views.ToolbarItemViewFactory;
import com.strato.hidrive.views.contextbar.toolbar.views.ToolbarPopupView;
import com.strato.hidrive.views.contextbar.toolbar.views.toolbar_view_style.LeftAlignmentToolbarViewStyle;
import com.strato.hidrive.views.contextbar.toolbar.views.toolbar_view_style.ToolbarItemViewStyle;
import com.strato.hidrive.views.contextbar.utils.ContextUtils;
import com.strato.hidrive.views.contextbar.utils.ParamAction;
import com.strato.hidrive.views.contextbar.utils.ScreenConfiguration;
import com.strato.hidrive.views.contextbar.utils.TimeSkippableActionExecutor;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;


public class ContextActionBar extends RelativeLayout implements ToolbarView {

	public enum MoreMenuPosition {
		TOP_POSITION,
		BOTTOM_POSITION
	}

	private static final int ALLOWED_REPEAT_DELAY = 500;

	private static ToolbarItemViewStyle normalToolbarItemViewStyle;

	private final TimeSkippableActionExecutor skippableActionExecutor = new TimeSkippableActionExecutor();

	@Inject
	ToolbarItemViewFactory toolbarItemViewFactory;

	private ICABModeStrategy modeStrategy;
	private ICABConfigurationStrategy configurationStrategy;
	private ToolbarItemClickListener toolbarItemClickListener = NullToolbarItemClickListener.INSTANCE;
	private PopupWindow pwMore;
	private MoreMenuPosition moreMenuPosition = MoreMenuPosition.BOTTOM_POSITION;
	private ToolbarItemViewStyle toolbarItemViewStyle;
	private final ResourceBundle resourceBundle = new ResourceBundle();
	private List<ToolbarItem> items;
	private Optional<PopupHeaderBundle> popupHeaderBundleOptional;

	private LinearLayout llRoot;
	private LinearLayout llButtonsPlace;
	private ImageButton ibMore;

	public static void init(ToolbarItemViewStyle normalToolbarItemViewStyle) {
		ContextActionBar.normalToolbarItemViewStyle = normalToolbarItemViewStyle;
	}

	public ContextActionBar(Context context) {
		super(context);
		initialize();
	}

	public ContextActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
		readAttrs(attrs);
	}

	public ContextActionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	private void readAttrs(AttributeSet attrs) {
		if (isInEditMode()) {
			return;
		}
		TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ContextActionBar, 0, 0);
		if (array.getIndexCount() > 0) {
			try {
				this.ibMore.setImageDrawable(array.getDrawable(R.styleable.ContextActionBar_cab_more_icon));
				this.resourceBundle.setTopItemSelector(array.getResourceId(R.styleable.ContextActionBar_cab_top_item_selector, 0));
				this.resourceBundle.setBottomItemSelector(array.getResourceId(R.styleable.ContextActionBar_cab_bottom_item_selector, 0));
				this.resourceBundle.setItemSelector(array.getResourceId(R.styleable.ContextActionBar_cab_item_selector, 0));
				this.resourceBundle.setTopBottomItemSelector(array.getResourceId(R.styleable.ContextActionBar_cab_top_bottom_item_selector, 0));
				this.resourceBundle.setFontColor(array.getResourceId(R.styleable.ContextActionBar_cab_more_font_color, Color.WHITE));
				this.resourceBundle.setDividerColor(array.getDrawable(R.styleable.ContextActionBar_cab_more_divider));
				this.resourceBundle.setMenuWidth(
						array.getDimensionPixelSize(
								R.styleable.ContextActionBar_cab_more_width,
								getResources().getDimensionPixelOffset(R.dimen.contextbar_more_popup_width)));
				this.resourceBundle.setMenuElevationPadding(
						array.getDimensionPixelSize(R.styleable.ContextActionBar_cab_more_elevation_padding, 0));
			} finally {
				array.recycle();
			}
		}
	}

	public void setMenuPaddingTop(int paddingInPx) {
		this.resourceBundle.setMenuPaddingTop(paddingInPx);
	}

	private void initialize() {
		if (isInEditMode()) {
			return;
		}
		ContextbarComponent.Companion.from(getContext()).inject(this);
		inflateView();
		initControls();
		disablePassingOfTouchesToUnderviews();
		this.toolbarItemViewStyle = normalToolbarItemViewStyle;
	}

	private void inflateView() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_context_action_bar, this, true);
	}

	private void initControls() {
		this.llButtonsPlace = findViewById(R.id.llButtonsPlace);
		this.llRoot = findViewById(R.id.llRoot);
		this.ibMore = findViewById(R.id.ibMore);
		this.ibMore.setOnClickListener(this.moreMenuButtonClickListener);
	}

	private void disablePassingOfTouchesToUnderviews() {
		this.llRoot.setOnTouchListener((arg0, arg1) -> true);
	}

	private void updateAlignment() {
		if (this.configurationStrategy.getCABModeStrategy().isLeftAligned()) {
			alignButtonsLeft();
		}
	}

	protected void alignButtonsLeft() {
		this.toolbarItemViewStyle = new LeftAlignmentToolbarViewStyle();
		arrangeToolbarItemViews(this.toolbarItemViewStyle);
	}

	private void arrangeToolbarItemViews(ToolbarItemViewStyle toolbarViewArranger) {
		for (int i = 0; i < this.llButtonsPlace.getChildCount(); i++) {
			toolbarViewArranger.applyToolbarItemStyle(getContext(), this.llButtonsPlace.getChildAt(i));
		}
	}

	public void changeMode(ICABModeStrategy modeStrategy) {
		this.modeStrategy = modeStrategy;
		updateRootBackground();
		updateAlignment();
	}

	private void updateRootBackground() {
		llRoot.setBackgroundResource(this.modeStrategy.getRootLayoutBackgroundColor());
	}

	private void updateToolbarItems() {
		displayItemsInToolbar(ToolbarItem.filter(this.items, ToolbarItemPosition.TOOLBAR));
		displayItemsInPopup(ToolbarItem.filter(this.items, ToolbarItemPosition.POPUP));
	}

	private void displayItemsInToolbar(List<ToolbarItem> items) {
		this.llButtonsPlace.removeAllViews();
		for (ToolbarItem item : items) {
			ToolbarItemView child = this.toolbarItemViewFactory.create(getContext(), item);
			child.setOnToolbarItemViewClickListener(new ParamAction<ToolbarItem>() {
				@Override
				public void execute(ToolbarItem value) {
					handleToolbarItemClick(value);
				}
			});
			addToolbarItemViewToButtonsPlace(child, ToolbarItemBundle.isTextToolbarItem(item) && new ScreenConfiguration().large(getContext()));
		}
		arrangeToolbarItemViews(this.toolbarItemViewStyle);
	}

	private void addToolbarItemViewToButtonsPlace(ToolbarItemView toolbarItemView, boolean isTextToolbarItemAndTablet) {
		LinearLayout placeholder = new LinearLayout(getContext());
		placeholder.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		placeholder.setGravity(Gravity.CENTER);
		if(isTextToolbarItemAndTablet) placeholder.setPadding(0, 0, (int) getResources().getDimension(R.dimen.contextbar_app_bar_elevation), 0);
		placeholder.addView(toolbarItemView);
		this.llButtonsPlace.addView(placeholder);
	}

	private ToolbarItemView findToolbarItemViewInButtonsPlace(int position) {
		ViewGroup placeholder = (ViewGroup) this.llButtonsPlace.getChildAt(position);
		if (placeholder != null) {
			View child = placeholder.getChildAt(0);
			if (child != null) {
				return (ToolbarItemView) child;
			}
		}
		return null;
	}

	private void displayItemsInPopup(List<ToolbarItem> items) {
		if (!items.isEmpty()) {
			this.ibMore.setVisibility(View.VISIBLE);
			createPopupView(items);
		} else {
			this.ibMore.setVisibility(View.GONE);
		}
	}

	private void createPopupView(List<ToolbarItem> items) {
		ToolbarPopupView popupView = getToolbarPopupView(items);

		if (this.pwMore == null) {
			this.pwMore = new StylizedPopupWindow(popupView, this.resourceBundle.getMenuWidth(), LayoutParams.WRAP_CONTENT, true);
			this.pwMore.setOnDismissListener(() -> executeToolbarItemClick(ToolbarItem.createEmptyToolbarItem(ToolbarItemType.MORE_CLOSE)));
		} else {
			this.pwMore.setContentView(popupView);
			this.pwMore.setWidth(this.resourceBundle.getMenuWidth());
			this.pwMore.setHeight(LayoutParams.WRAP_CONTENT);
			this.pwMore.setFocusable(true);
		}
	}

	private ToolbarPopupView getToolbarPopupView(List<ToolbarItem> items) {
		ToolbarPopupView popupView = new ToolbarPopupView(getContext());
		popupView.setListener(this.toolbarPopupViewListener);
		if (this.popupHeaderBundleOptional.isPresent()) {
			popupView.setPopupHeaderBundleOptional(this.popupHeaderBundleOptional.get());
		}
		popupView.setToolbarItems(items);
		popupView.setResourceBundle(this.resourceBundle);
		return popupView;
	}

	public void showMoreMenu() {
		if (this.pwMore != null && !ContextUtils.isActivityFinishing(getContext())) {
			this.pwMore.showAtLocation(
					this,
					getMoreMenuGravity(),
					getPopupOffsetX(),
					getPopupOffsetY());
		}
	}

	private int getMoreMenuGravity() {
		return (isAtBottomPosition() ? Gravity.BOTTOM : Gravity.TOP) | Gravity.RIGHT;
	}

	private boolean isAtBottomPosition() {
		return this.moreMenuPosition.equals(MoreMenuPosition.BOTTOM_POSITION);
	}

	private int getPopupOffsetX() {
		int popupOffsetX = getResources().getDimensionPixelSize(R.dimen.contextbar_more_popup_right_padding);
		return popupOffsetX - this.resourceBundle.getMenuElevationPadding();
	}

	private int getPopupOffsetY() {
		int popupOffsetY = getResources().getDimensionPixelSize(R.dimen.contextbar_more_popup_top_padding);
		return popupOffsetY + this.resourceBundle.getMenuPaddingTop() - this.resourceBundle.getMenuElevationPadding();
	}

	public void dismissMoreMenu() {
		if (this.pwMore != null) {
			this.pwMore.dismiss();
		}
	}

	public void setCompactStyle(ToolbarItemViewStyle toolbarItemViewStyle) {
		this.llButtonsPlace.setGravity(Gravity.RIGHT);
		this.toolbarItemViewStyle = toolbarItemViewStyle;
	}

	public void setDefaultStyle(ToolbarItemViewStyle toolbarItemViewStyle) {
		this.llButtonsPlace.setGravity(Gravity.NO_GRAVITY);
		this.toolbarItemViewStyle = toolbarItemViewStyle;
	}

	public ToolbarItem findToolbarItem(ToolbarItemType type) {
		for (ToolbarItem item : this.items) {
			if (item.getType() == type) {
				return item.copy();
			}
		}
		return null;
	}

	public ToolbarItemView findToolbarItemView(ToolbarItemType type) {
		for (int i = 0; i < this.llButtonsPlace.getChildCount(); i++) {
			ToolbarItemView toolbarItemView = findToolbarItemViewInButtonsPlace(i);
			if (toolbarItemView != null && toolbarItemView.getItem().getType().equals(type)) {
				return toolbarItemView;
			}
		}
		return null;
	}

	public void updateToolbarItem(ToolbarItem item) {
		boolean foundExistedItem = false;
		for (int i = 0; i < this.items.size(); i++) {
			if (this.items.get(i).getType() == item.getType()) {
				this.items.remove(i);
				this.items.add(i, item.copy());
				foundExistedItem = true;
				break;
			}
		}
		if (foundExistedItem) {
			updateToolbarItems();
		}
	}

	private void handleToolbarItemClick(final ToolbarItem item) {
		this.skippableActionExecutor.execute(() -> executeToolbarItemClick(item), ALLOWED_REPEAT_DELAY);
	}

	private void executeToolbarItemClick(ToolbarItem item) {
		this.toolbarItemClickListener.onToolbarItemClick(item);
	}

	private final ToolbarPopupView.ToolbarPopupViewListener toolbarPopupViewListener = item -> {
		dismissMoreMenu();
		handleToolbarItemClick(item);
	};

	private final OnClickListener moreMenuButtonClickListener = v -> {
		showMoreMenu();
		handleToolbarItemClick(ToolbarItem.createEmptyToolbarItem(ToolbarItemType.MORE));
	};

	public void setMoreMenuPosition(MoreMenuPosition moreMenuPosition) {
		this.moreMenuPosition = moreMenuPosition;
	}

	//region ToolbarView

	@Override
	public void setToolbarItemClickListener(ToolbarItemClickListener listener) {
		this.toolbarItemClickListener = listener != null
				? listener
				: NullToolbarItemClickListener.INSTANCE;
	}

	@Override
	public void setToolbarStrategy(ICABConfigurationStrategy strategy) {
		this.configurationStrategy = strategy;
		this.items = this.configurationStrategy.getToolbarItems();
		this.popupHeaderBundleOptional = strategy.getPopupHeaderBundle();
		updateToolbarItems();
		changeMode(this.configurationStrategy.getCABModeStrategy());
	}

	//endregion
}
