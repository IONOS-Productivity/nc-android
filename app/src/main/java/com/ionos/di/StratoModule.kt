package com.ionos.di

import com.ionos.scanbot.di.NCScanbotModule
import dagger.Module

@Module(includes = [NCScanbotModule::class])
abstract class StratoModule