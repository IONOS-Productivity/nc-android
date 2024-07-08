package com.ionos.scanbot.utils

import com.ionos.scanbot.util.GetLocalFreeSpace
import com.owncloud.android.utils.FileStorageUtils
import io.reactivex.Single
import javax.inject.Inject

class GetLocalFreeSpaceImpl @Inject constructor() : GetLocalFreeSpace {
    override fun invoke(): Single<Long> =
        Single.fromCallable { FileStorageUtils.getUsableSpace() }
}