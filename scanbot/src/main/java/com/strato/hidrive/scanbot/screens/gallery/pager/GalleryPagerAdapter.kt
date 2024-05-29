package com.strato.hidrive.scanbot.screens.gallery.pager

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.strato.hidrive.scanbot.entity.Picture
import com.strato.hidrive.scanbot.repository.RepositoryFacade
import javax.inject.Inject

internal class GalleryPagerAdapter @Inject constructor(
	// private val imageLoader: ImageLoader,
	private val repositoryFacade: RepositoryFacade,
) : PagerAdapter() {

	private val pictures: MutableList<Picture> = mutableListOf()

	internal fun setPictures(pictures: List<Picture>) {
		this.pictures.clear()
		this.pictures.addAll(pictures)
		notifyDataSetChanged()
	}

	override fun isViewFromObject(view: View, item: Any): Boolean {
		return item is GalleryPagerItem && item.imageView == view
	}

	override fun getCount(): Int = pictures.size

	override fun getItemPosition(item: Any): Int {
		return if (item is GalleryPagerItem) {
			val index = pictures.indexOf(item.picture)
			if (index > -1) index else POSITION_NONE
		} else {
			super.getItemPosition(item)
		}
	}

	override fun instantiateItem(container: ViewGroup, position: Int): GalleryPagerItem {
		val imageView = ImageView(container.context)
		val item = GalleryPagerItem(pictures[position], imageView)
		container.addView(imageView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
		imageView.loadPicture(pictures[position])
		return item
	}

	override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
		if (item is GalleryPagerItem) {
			container.removeView(item.imageView)
		}
	}

	private fun ImageView.loadPicture(picture: Picture) {
		//todo alk

        // repositoryFacade.readModifiedFile(picture.id)
		// 	?.let(imageLoader::load)
		// 	?.options(ImageLoaderOptionsBundle(ScaleType.CENTER_INSIDE))
		// 	?.into(SimpleDrawableTarget(this))
	}
}
