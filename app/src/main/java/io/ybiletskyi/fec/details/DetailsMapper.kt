package io.ybiletskyi.fec.details

import android.text.format.DateFormat
import io.ybiletskyi.domain.Email
import java.util.*

class DetailsMapper {

    fun mapData(email: Email): DetailData.EmailDetail {
        return DetailData.EmailDetail(
            id = email.id,
            subject = email.subject,
            sender = email.from,
            description = email.text,
            date = getDate(email.time),
            isRead = !email.isViewed,
            isDeleted = email.isDeleted
        )
    }

    private fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        return DateFormat.format("dd.MM.yyyy", calendar).toString()
    }
}