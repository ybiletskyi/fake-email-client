package io.ybiletskyi.domain.stores.http

import io.ybiletskyi.domain.Email
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

internal class EmailsParser {

    fun parseList(json: String): List<Email>? {
        val root = JSONParser().parse(json) as? JSONArray

        return root?.mapNotNull { emailObject ->
            val jsonEmail = emailObject as? JSONObject
            return@mapNotNull parseEmail(jsonEmail)
        }
    }

    fun parseEmail(json: String): Email? {
        return parseList(json)?.firstOrNull()
    }

    private fun parseEmail(jsonEmail: JSONObject?): Email? {
        return jsonEmail?.run {
            Email(
                id = asInt("id", -1),
                time = asLong("time", -1),
                from = asString("from", ""),
                subject = asString("subject", ""),
                text = asString("text", ""),
                isViewed = asBoolean("isViewed", false),
                isDeleted = asBoolean("isDeleted", false)
            )
        }
    }
}