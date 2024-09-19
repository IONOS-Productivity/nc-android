package com.strato.hidrive.player.views.player.view

import com.strato.hidrive.player.views.player.fragment.SurfaceInvalidator

interface PlayerCompatible {

	fun getSurfaceInvalidator() : SurfaceInvalidator

}