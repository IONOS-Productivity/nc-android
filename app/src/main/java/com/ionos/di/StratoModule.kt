package com.ionos.di

import com.ionos.exif_info.di.NCExifInfoModule
import com.ionos.player.di.NCPlayerModule
import dagger.Module

@Module(includes = [NCPlayerModule::class, NCExifInfoModule::class])
abstract class StratoModule