package io.ybiletskyi.fec.main

sealed class ShortData {
    data class EmailShortData(
        val subject: String,
        val sender: String,
        val description: String,
        val date: String,
        val isActive: Boolean
    ) : ShortData()
}