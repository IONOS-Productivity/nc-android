/*
 * IONOS HiDrive Next - Android Client
 *
 * SPDX-FileCopyrightText: 2025 STRATO Gmbh.
 * SPDX-License-Identifier: GPL-2.0
 */

package com.owncloud.android.ui.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.ionos.player.model.ThumbnailLoader
import com.owncloud.android.databinding.GallerySimpleItemBinding
import com.owncloud.android.datamodel.OCFile
import com.owncloud.android.ui.fragment.GalleryFragment
import com.owncloud.android.ui.interfaces.OCFileListFragmentInterface
import com.owncloud.android.utils.glide.CustomGlideStreamLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GallerySimpleItemHolder(
    private val binding: GallerySimpleItemBinding,
    private val thumbnailSize: Int,
    private val getThumbnailUrl: String,
    private val modelLoader: CustomGlideStreamLoader,
    private val ocFileListFragmentInterface: OCFileListFragmentInterface
) : ViewHolder(binding.root) {

    fun bind(file: OCFile) {
    private val thumbnailLoader: ThumbnailLoader,
    private val coroutineScope: CoroutineScope,
    private val ocFileListFragmentInterface: OCFileListFragmentInterface
) : ViewHolder(binding.root) {

    private var loadJob: Job? = null

    fun bind(file: OCFile) {
        loadJob?.cancel()

        binding.thumbnail.setOnClickListener {
            ocFileListFragmentInterface.onItemClicked(file)
            GalleryFragment.setLastMediaItemPosition(absoluteAdapterPosition)
        }

        Glide
            .with(itemView.context)
            .using(modelLoader)
            .load("$getThumbnailUrl?fileId=${file.localId}&x=$thumbnailSize&y=$thumbnailSize&a=1&mode=cover&forceIcon=0")
            .placeholder(com.elyeproj.loaderviewlibrary.R.color.default_color)
            .override(thumbnailSize, thumbnailSize)
            .into(binding.thumbnail)
    }
}
        binding.thumbnail.setImageResource(com.elyeproj.loaderviewlibrary.R.color.default_color)

        val url = "$getThumbnailUrl?fileId=${file.localId}&x=$thumbnailSize&y=$thumbnailSize&a=1&mode=cover&forceIcon=0"

        loadJob = coroutineScope.launch {
            thumbnailLoader.load(binding.thumbnail, url, file.localId.toString())
        }
    }

    fun recycle() {
        loadJob?.cancel()
        loadJob = null
    }
}