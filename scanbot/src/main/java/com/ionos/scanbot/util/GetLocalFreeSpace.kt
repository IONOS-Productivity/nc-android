package com.ionos.scanbot.util

import io.reactivex.Single

interface GetLocalFreeSpace {
    operator fun invoke(): Single<Long>
}