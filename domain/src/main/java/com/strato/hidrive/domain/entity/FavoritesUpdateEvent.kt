package com.strato.hidrive.domain.entity

sealed interface FavoritesUpdateEvent {
    class StatusChanged(val changedFileInfo: List<RemoteFileInfo>) : FavoritesUpdateEvent
    class FilesAddedSuccessfully(val countOfFiles: Int) : FavoritesUpdateEvent
    class Error(val throwable: Throwable) : FavoritesUpdateEvent
    class FileCancelOfflineAvailability(val files: List<RemoteFileInfo>) : FavoritesUpdateEvent
}