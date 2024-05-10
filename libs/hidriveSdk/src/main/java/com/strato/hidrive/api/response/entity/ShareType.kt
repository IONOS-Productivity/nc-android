package com.strato.hidrive.api.response.entity

/**
 * User: Dima Muravyov
 * Date: 30.05.2018
 */
enum class ShareType(val value: String) {
	Directory("sharedir"),
	File("sharelink"),
	ShareUpload(" shareupload"),
	MailUpload("mailupload")
}