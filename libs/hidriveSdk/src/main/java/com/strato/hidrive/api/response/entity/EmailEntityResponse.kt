package com.strato.hidrive.api.response.entity

/**
 * User: Dima Muravyov
 * Date: 02.03.2018
 */
data class EmailEntityResponse(val done: List<EmailRecipientEntityResponse>?,
							   val failed: List<EmailRecipientEntityResponse>?)