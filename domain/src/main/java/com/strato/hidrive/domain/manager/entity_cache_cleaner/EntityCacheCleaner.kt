package com.strato.hidrive.domain.manager.entity_cache_cleaner

/**
 * User: Dima Muravyov
 * Date: 31.07.2018
 */
interface EntityCacheCleaner<Entity> {
	fun clean(entity: Entity)
}