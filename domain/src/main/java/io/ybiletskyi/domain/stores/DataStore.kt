package io.ybiletskyi.domain.stores

import io.ybiletskyi.domain.Email
import io.ybiletskyi.domain.Result

interface DataStore {
    suspend fun emails(page: Int, limit: Int): Result<List<Email>>
}