package com.strato.hidrive.domain.repository

import io.reactivex.Maybe

interface PidRepository {
	operator fun get(path: String): Maybe<String>
}
