package io.ybiletskyi.domain.http

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.simple.JSONObject
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun Call.await(): Response {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                continuation.resume(response)
            }

            override fun onFailure(call: Call, e: IOException) {
                if (continuation.isCancelled)
                    return
                continuation.resumeWithException(e)
            }
        })

        continuation.invokeOnCancellation {
            try {
                cancel()
            } catch (ex: Throwable) {
                // ignore cancel exception
            }
        }
    }
}

fun JSONObject.asInt(key: Any?, default: Int): Int {
    return this.asInt(key) ?: default
}

fun JSONObject.asInt(key: Any?): Int? {
    val data = this[key]
    return (when (data) {
        is String -> data.toIntOrNull()
        is Number -> data.toInt()
        else -> null
    })
}

fun JSONObject.asLong(key: Any?, default: Long): Long {
    return this.asLong(key) ?: default
}

fun JSONObject.asLong(key: Any?): Long? {
    val data = this[key]
    return (when (data) {
        is String -> data.toLongOrNull()
        is Number -> data.toLong()
        else -> null
    })
}

fun JSONObject.asString(key: Any?): String? {
    return this[key] as? String?
}

fun JSONObject.asString(key: Any?, default: String): String {
    return this.asString(key) ?: default
}