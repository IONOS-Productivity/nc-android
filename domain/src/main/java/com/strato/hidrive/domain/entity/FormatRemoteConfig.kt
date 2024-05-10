package com.strato.hidrive.domain.entity

/**
 * User: Vadym Fedchuk
 * Date: 21.11.2023
 */

data class FormatRemoteConfig(
    val category: FileCategory,
    val categorySubName: FileCategory?,
    val supportedByCollabora: Boolean,
    val supportsThumbnail: Boolean,
    val apiEditable: Boolean,
)
