package com.strato.hidrive.player.views.player.fragment

import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SurfaceInvalidator {

	private val listOfSurfaces = mutableListOf<View>()
	private var disposable = Disposables.disposed()

	fun invalidateSurfaceViews() {
		disposable.dispose()

		disposable = Completable.complete()
			.delay(150L, TimeUnit.MILLISECONDS)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{
					listOfSurfaces.forEach {
						it.visibility = INVISIBLE
						it.visibility = VISIBLE
					}
				}
			) { Log.e(SurfaceInvalidator::class.java.simpleName, "", it) }
	}

	fun addSurface(surface: View) {
		listOfSurfaces.add(surface)
	}

	fun removeSurface(surface: View) {
		listOfSurfaces.remove(surface)
	}

	fun cleanUp() {
		listOfSurfaces.clear()
	}
}