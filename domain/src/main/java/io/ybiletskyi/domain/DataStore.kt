package io.ybiletskyi.domain

interface DataStore {
    suspend fun emails(page: Int, limit: Int): Result<List<Email>>
}