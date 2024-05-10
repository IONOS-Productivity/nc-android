package com.strato.hidrive.domain

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface UseCase<Result> {
	operator fun invoke(): Result
}

@Deprecated("Use factories instead")
interface MaybeUseCase<Entity> : UseCase<Maybe<Entity>>

@Deprecated("Use factories instead")
interface SingleUseCase<Entity> : UseCase<Single<Entity>>

@Deprecated("Use factories instead")
interface CompletableUseCase : UseCase<Completable>

interface Factory<Input, Result> {
	fun create(input: Input): Result
}

interface SingleUseCaseFactory<Input, Result> : Factory<Input, Single<Result>>

interface MaybeUseCaseFactory<Input, Result> : Factory<Input, Maybe<Result>>

interface CompletableUseCaseFactory<Input> : Factory<Input, Completable>

interface ObservableUseCaseFactory<Input, Result> : Factory<Input, Observable<Result>>

interface SuspendUseCaseFactory<Input, Result> : Factory<Input, suspend () -> Result>

interface FlowUseCaseFactory<Input, Result> : Factory<Input, Flow<Result>>