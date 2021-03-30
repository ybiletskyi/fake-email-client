package io.ybiletskyi.fec.main

import android.text.format.DateFormat
import io.ybiletskyi.domain.Email
import java.util.*

class EmailsMapper {

    fun mapData(email: Email): ShortData.EmailShortData {
        return ShortData.EmailShortData(
            subject = email.subject,
            sender = email.from,
            description = email.text,
            date = getDate(email.time),
            isActive = email.isViewed
        )
    }

    private fun getDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        return DateFormat.format("dd.MM.yyyy", calendar).toString()
    }
}