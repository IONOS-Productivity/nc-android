package com.strato.hidrive.api.response.entity

import com.google.gson.annotations.SerializedName

data class FormatRemoteConfigResponse(
	@SerializedName("data")
    val data: List<Data?>?,
	@SerializedName("version")
    val version: Int?
)

data class Data(
	@SerializedName("category")
    val category: String,
	@SerializedName("categorySubname")
    val categorySubname: String?,
	@SerializedName("extensions")
    val extensions: List<String?>?,
	@SerializedName("officeEditable")
    val officeEditable: Boolean,
	@SerializedName("supportsThumbnail")
    val supportsThumbnail: Boolean,
	@SerializedName("apiEditable")
	val apiEditable: Boolean,
)