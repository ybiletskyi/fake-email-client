package io.ybiletskyi.domain

data class Email(
    val id: Int,
    val time: Long,
    val from: String,
    val subject: String,
    val text: String,
    val isViewed: Boolean,
    val isDeleted: Boolean
)