package io.ybiletskyi.domain.http.client

import io.ybiletskyi.domain.http.await
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

object HttpClient {

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

        return when {
            response.isSuccessful && response.body != null -> HttpStatus.Success(response.body.toString())
            else -> HttpStatus.Error("Bad response")
        }
    }
}

