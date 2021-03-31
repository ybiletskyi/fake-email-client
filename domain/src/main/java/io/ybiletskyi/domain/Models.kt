package io.ybiletskyi.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Email(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "time") val time: Long,
    @ColumnInfo(name = "from") val from: String,
    @ColumnInfo(name = "subject") val subject: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "is_viewed") val isViewed: Boolean,
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean
)