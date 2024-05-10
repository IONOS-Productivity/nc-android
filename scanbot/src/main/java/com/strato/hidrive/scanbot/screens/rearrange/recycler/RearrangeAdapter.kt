package com.strato.hidrive.scanbot.screens.rearrange.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.strato.hidrive.common_ui.image.loader.ImageLoader
import com.strato.hidrive.common_ui.utils.DiffUtilsUpdateCallback
import com.strato.hidrive.scanbot.databinding.ScanbotItemRearrangeBinding
import com.strato.hidrive.scanbot.repository.RepositoryFacade
import javax.inject.Inject

internal class RearrangeAdapter @Inject constructor(
    //TODO alk
	// private val imageLoader: ImageLoader,
	private val repositoryFacade: RepositoryFacade,
) : RecyclerView.Adapter<RearrangeViewHolder>() {

	private var items = emptyList<RearrangeItem>()

	fun setItems(items: List<RearrangeItem>) {
		val diffCallback = RearrangeDiffCallback(this.items, items)
		val diff = DiffUtil.calculateDiff(diffCallback)
		this.items = items
		diff.dispatchUpdatesTo(DiffUtilsUpdateCallback(this))
	}

	override fun getItemCount(): Int = items.size

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RearrangeViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val viewBinding = ScanbotItemRearrangeBinding.inflate(inflater, parent, false)
		return RearrangeViewHolder(viewBinding, /*imageLoader,*/ repositoryFacade)
	}

	override fun onBindViewHolder(holder: RearrangeViewHolder, position: Int) {
		holder.bind(items[position])
	}
}
