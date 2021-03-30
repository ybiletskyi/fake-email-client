package io.ybiletskyi.fec.details

data class EmailDetail(
        val id: Int,
        val subject: String,
        val sender: String,
        val description: String,
        val date: String,
        val isViewed: Boolean,
        val isDeleted: Boolean
)