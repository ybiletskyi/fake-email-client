package io.ybiletskyi.fec.main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class ShortData {
    // indicate that data is loading
    object Loading: ShortData()
    // indicate error/message during data loading
    data class InfoMessage(val message: String): ShortData()
    // valid data
    @Parcelize
    data class EmailShortData(
        val id: Int,
        val subject: String,
        val sender: String,
        val description: String,
        val date: String,
        val isDeleted: Boolean,
        val isRead: Boolean): ShortData(), Parcelable
}