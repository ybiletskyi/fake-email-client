package io.ybiletskyi.domain.http

import io.ybiletskyi.domain.DataStore
import io.ybiletskyi.domain.Email
import io.ybiletskyi.domain.Result
import io.ybiletskyi.domain.http.client.*

class HttpDataStore(
    private val httpClient: HttpClient,
    private val parser: EmailsParser
): DataStore {

    companion object {
        fun newInstance(): DataStore {
            return HttpDataStore(HttpClient, EmailsParser())
        }
    }

    override suspend fun emails(page: Int, limit: Int): Result<List<Email>> {
        val url = UrlResolver(Url.EmailsUrl)
            .param(UrlArg.Page, page)
            .param(UrlArg.Limit, limit)
            .build()

        return when(val status = httpClient.get(url)) {
            is HttpStatus.Error -> Result.Error(status.error)
            is HttpStatus.Success -> {
                val list = parser.parse(status.json)
                return when {
                    list != null -> Result.Success(list)
                    else -> Result.Error("Bad json")
                }
            }
        }
    }
}