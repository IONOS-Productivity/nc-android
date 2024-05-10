package com.strato.hidrive.common_ui.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

public class DiffUtilsUpdateCallback implements ListUpdateCallback {

	private final RecyclerView.Adapter<?> adapter;

	public DiffUtilsUpdateCallback(@NonNull RecyclerView.Adapter<?> adapter) {
		this.adapter = adapter;
	}

	@Override
	public void onInserted(int position, int count) {
		adapter.notifyItemRangeInserted(position, count);
	}

	@Override
	public void onRemoved(int position, int count) {
		adapter.notifyItemRangeRemoved(position, count);
	}

	@Override
	public void onMoved(int fromPosition, int toPosition) {
		adapter.notifyItemMoved(fromPosition, toPosition);
	}

	@Override
	public void onChanged(int position, int count, @Nullable Object payload) {
		// payload object may be null, causing creation of new viewHolder when it's
		// not desired. any non null object as payload prevents this behavior
		adapter.notifyItemRangeChanged(position, count, "Non null");
	}
}
