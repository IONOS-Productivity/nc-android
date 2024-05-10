package com.strato.hidrive.domain.background.jobs

/**
 * Created by y.zozulia on 17.04.2015.
 */
enum class BackgroundJobStatus(val id: Int) {
	IN_QUEUE(1),
	IN_PROGRESS(2),
	PAUSED(3),
	LOADED(4),
	FAILED(5),
	CANCELED(6),
	;

	companion object {
		fun byId(id: Int): BackgroundJobStatus {
			return values().first { it.id == id }
		}
	}
}