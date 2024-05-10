package com.strato.hidrive.domain.utils.availability;

import io.reactivex.Single;

public interface RxAvailability {
	Single<Boolean> available();
}
