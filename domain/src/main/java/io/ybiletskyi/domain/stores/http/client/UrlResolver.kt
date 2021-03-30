package io.ybiletskyi.domain.stores.http.client

import java.lang.StringBuilder

internal enum class Url(val path: String) {
    HostUrl("https://my-json-server.typicode.com/ybiletskyi/fake-email-client"),

    // end points
    EmailsUrl("${HostUrl.path}/emails")
}

internal enum class UrlArg(val arg: String) {
    Page("_start"),
    Limit("_limit")
}

internal class UrlResolver(
    private val url: Url,
    private val params: MutableMap<UrlArg, Any> = mutableMapOf()
) {

    fun param(key: UrlArg, value: Any): UrlResolver {
        params[key] = value
        return this
    }

    fun build(): String {
        if (params.isEmpty())
            return url.path

        val builder = StringBuilder()
        builder.append(url.path)
        builder.append("?")
        params.forEach { entry -> builder.append("${entry.key.arg}=${entry.value}") }

        return builder.toString()
    }
}