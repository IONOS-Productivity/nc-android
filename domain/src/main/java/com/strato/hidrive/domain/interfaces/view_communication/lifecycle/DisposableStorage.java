package com.strato.hidrive.domain.interfaces.view_communication.lifecycle;

import androidx.annotation.Nullable;

import io.reactivex.disposables.Disposable;

/**
 * For background jobs that are dependent on view lifecycle and no longer needed after view was destroyed
 */
public interface DisposableStorage {
	void addDisposableJob(@Nullable Disposable disposableJob);
}
