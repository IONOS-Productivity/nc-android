package com.strato.hidrive.scanbot.screens.rearrange.recycler

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.strato.hidrive.common_ui.image.loader.ImageLoader
import com.strato.hidrive.common_ui.image.loader.options.ImageLoaderOptionsBundle
import com.strato.hidrive.common_ui.image.loader.options.ScaleType
import com.strato.hidrive.common_ui.image.loader.target.SimpleDrawableTarget
import com.strato.hidrive.scanbot.databinding.ScanbotItemRearrangeBinding
import com.strato.hidrive.scanbot.entity.Picture
import com.strato.hidrive.scanbot.repository.RepositoryFacade

internal class RearrangeViewHolder(
	private val viewBinding: ScanbotItemRearrangeBinding,
    //TODO alk
    // private val imageLoader: ImageLoader,
	private val repositoryFacade: RepositoryFacade,
) : RecyclerView.ViewHolder(viewBinding.root) {

	fun bind(item: RearrangeItem) = with(viewBinding) {
		tvSequentialNumber.text = item.sequenceNumber.toString()
		ivRearrangePreview.loadPicture(item.picture)
	}

	private fun ImageView.loadPicture(picture: Picture) {
		// repositoryFacade.readModifiedFile(picture.id)
			// ?.let(imageLoader::load)
			// ?.options(ImageLoaderOptionsBundle(ScaleType.CENTER_INSIDE))
			// ?.into(SimpleDrawableTarget(this))
	}
}
