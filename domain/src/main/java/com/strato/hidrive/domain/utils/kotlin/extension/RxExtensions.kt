package com.strato.hidrive.domain.utils.kotlin.extension
//
import com.strato.hidrive.domain.logger.LoggerUtil
import io.reactivex.CompletableEmitter
import io.reactivex.ObservableEmitter
import io.reactivex.SingleEmitter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.UndeliverableException
//
/**
 * Created by: Alex Kucherenko
 * Date: 28.09.2018.
 */
fun Disposable?.safeDispose() {
	if (this != null && !isDisposed) {
		dispose()
	}
}

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
	add(disposable)
}

// fun CompletableEmitter.checkedOnComplete() {
// 	if (!isDisposed) {
// 		tryOrLog { onComplete() }
// 	}
// }
//
// fun CompletableEmitter.checkedOnError(error: Throwable) {
// 	if (!isDisposed) {
// 		tryOrLog { tryOnError(error) }
// 	}
// }
//
// fun <T> SingleEmitter<T>.checkedOnSuccess(value: T) {
// 	if (!isDisposed) {
// 		tryOrLog { onSuccess(value) }
// 	}
// }
//
// fun <T> SingleEmitter<T>.checkedOnError(error: Throwable) {
// 	if (!isDisposed) {
// 		tryOrLog { tryOnError(error) }
// 	}
// }
//
// fun <T> ObservableEmitter<T>.checkedOnNext(value: T) {
// 	if (!isDisposed) {
// 		tryOrLog { onNext(value) }
// 	}
// }
//
// fun <T> ObservableEmitter<T>.checkedOnComplete() {
// 	if (!isDisposed) {
// 		tryOrLog { onComplete() }
// 	}
// }
//
// fun <T> ObservableEmitter<T>.checkedOnNextAndComplete(value: T) {
// 	if (!isDisposed) {
// 		tryOrLog {
// 			onNext(value)
// 			onComplete()
// 		}
// 	}
// }
//
// fun <T> ObservableEmitter<T>.checkedOnError(error: Throwable) {
// 	if (!isDisposed) {
// 		tryOrLog { tryOnError(error) }
// 	}
// }
//
// private inline fun <T : Any> T.tryOrLog(run: (T) -> Unit) {
// 	try {
// 		run(this)
// 	} catch (e: UndeliverableException) {
// 		LoggerUtil.logE(javaClass.simpleName, e)
// 	}
// }