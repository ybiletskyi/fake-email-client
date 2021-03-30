package io.ybiletskyi.fec.main

sealed class ShortData {
    // indicate that data is loading
    object Loading: ShortData()
    // indicate error/message during data loading
    data class InfoMessage(val message: String): ShortData()
    // valid data
    data class EmailShortData(
        val subject: String,
        val sender: String,
        val description: String,
        val date: String,
        val isActive: Boolean): ShortData()
}