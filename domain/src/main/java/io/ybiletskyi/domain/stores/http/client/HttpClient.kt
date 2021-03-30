package io.ybiletskyi.domain.stores.http.client

import io.ybiletskyi.domain.stores.http.await
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

internal object HttpClient {

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .writeTimeout(5000, TimeUnit.MILLISECONDS)
            .build()
    }

    suspend fun get(url: String): HttpStatus {
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = try {
            okHttpClient.newCall(request).await()
        } catch (e: IOException) {
            return HttpStatus.Error(e.message)
        }

        val responseStr = if (response.isSuccessful) {
            response.body?.string()
        } else null

        return when {
            responseStr != null -> HttpStatus.Success(responseStr)
            else -> HttpStatus.Error("Bad response")
        }
    }
}

