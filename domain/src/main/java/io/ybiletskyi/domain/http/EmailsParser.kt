package io.ybiletskyi.domain.http

import io.ybiletskyi.domain.Email
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

class EmailsParser {

    fun parse(json: String): List<Email>? {
        val root = JSONParser().parse(json) as? JSONArray

        return root?.mapNotNull { emailObject ->
            val jsonEmail = emailObject as? JSONObject
            return@mapNotNull parseEmail(jsonEmail)
        }
    }

    private fun parseEmail(jsonEmail: JSONObject?): Email? {
        return jsonEmail?.run {
            Email(
                asInt("id", -1),
                asLong("time", -1),
                asString("from", ""),
                asString("subject", ""),
                asString("text", "")
            )
        }
    }
}