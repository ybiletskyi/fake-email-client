package io.ybiletskyi.fec.details

sealed class DetailData {
    // indicate that data is loading
    object Loading: DetailData()
    // indicate error/message during data loading
    data class InfoMessage(val message: String): DetailData()
    // valid data
    data class EmailDetail(
        val id: Int,
        val subject: String,
        val sender: String,
        val description: String,
        val date: String,
        val isRead: Boolean,
        val isDeleted: Boolean): DetailData()
}