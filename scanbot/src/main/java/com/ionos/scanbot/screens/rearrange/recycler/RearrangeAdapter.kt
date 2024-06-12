package com.ionos.scanbot.screens.rearrange.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ionos.common_ui.utils.DiffUtilsUpdateCallback
import com.ionos.scanbot.databinding.ScanbotItemRearrangeBinding
import com.ionos.scanbot.image_loader.ImageLoader
import com.ionos.scanbot.repository.RepositoryFacade
import javax.inject.Inject

internal class RearrangeAdapter @Inject constructor(
	private val imageLoader: ImageLoader,
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
		return RearrangeViewHolder(viewBinding, imageLoader, repositoryFacade)
	}

	override fun onBindViewHolder(holder: RearrangeViewHolder, position: Int) {
		holder.bind(items[position])
	}
}
