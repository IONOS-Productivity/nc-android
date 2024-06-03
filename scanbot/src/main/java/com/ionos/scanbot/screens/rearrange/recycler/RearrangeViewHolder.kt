package com.ionos.scanbot.screens.rearrange.recycler

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ionos.scanbot.databinding.ScanbotItemRearrangeBinding
import com.ionos.scanbot.entity.Picture
import com.ionos.scanbot.repository.RepositoryFacade

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
