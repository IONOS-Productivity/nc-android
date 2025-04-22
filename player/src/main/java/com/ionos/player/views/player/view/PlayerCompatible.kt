package com.ionos.player.views.player.view

import com.ionos.player.views.player.fragment.SurfaceInvalidator

interface PlayerCompatible {

	fun getSurfaceInvalidator() : SurfaceInvalidator

}