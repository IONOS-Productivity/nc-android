package com.ionos.di

import com.ionos.player.di.NCPlayerModule
import dagger.Module

@Module(includes = [NCPlayerModule::class])
abstract class StratoModule