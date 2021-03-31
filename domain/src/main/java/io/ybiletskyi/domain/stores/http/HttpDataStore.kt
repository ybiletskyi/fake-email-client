package io.ybiletskyi.domain.stores.http

import io.ybiletskyi.domain.stores.DataStore
import io.ybiletskyi.domain.Email
import io.ybiletskyi.domain.Result
import io.ybiletskyi.domain.stores.http.client.*

internal class HttpDataStore(
    private val httpClient: HttpClient,
    private val parser: EmailsParser
): DataStore {

    companion object {
        fun newInstance(): DataStore {
            return HttpDataStore(HttpClient, EmailsParser())
        }
    }

    override suspend fun emails(page: Int, limit: Int, isDeleted: Boolean): Result<List<Email>> {
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

    override suspend fun saveEmails(emails: Collection<Email>): Result<Unit> {
        return Result.Error("Http store doesn't support save operation")
    }

    override suspend fun updateEmails(emails: Collection<Email>): Result<Unit> {
        return Result.Error("Http store doesn't support update operation")
    }
}