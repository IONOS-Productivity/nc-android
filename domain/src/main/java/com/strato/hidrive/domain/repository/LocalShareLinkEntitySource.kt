package com.strato.hidrive.domain.repository

import com.strato.hidrive.domain.entity.ShareLinkEntity
import com.strato.hidrive.domain.optional.Optional
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * User: Dima Muravyov
 * Date: 14.02.2019
 */
interface LocalShareLinkEntitySource {

	fun getEntities(): Single<List<ShareLinkEntity>>

	fun getEntities(parentPath: String): Single<List<ShareLinkEntity>>

	fun observeEntities(parentPath: String): Observable<List<ShareLinkEntity>>

	fun findByShareId(shareId: String): Single<Optional<ShareLinkEntity>>

	fun delete(vararg shareLinkEntity: ShareLinkEntity): Completable

	fun deleteAll(): Completable

	fun deleteShares(parentPath: String): Completable

	fun updateShares(parentPath: String, loadedShares: List<ShareLinkEntity>): Completable

	fun updateShares(parentPaths: List<String>, loadedShares: List<ShareLinkEntity>): Completable

	fun updateShares(shares: List<ShareLinkEntity>): Single<List<ShareLinkEntity>>

	fun updateShare(share: ShareLinkEntity): Single<ShareLinkEntity>
}