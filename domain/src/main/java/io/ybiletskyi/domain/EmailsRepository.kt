package io.ybiletskyi.domain

import io.ybiletskyi.domain.stores.http.HttpDataStore
import io.ybiletskyi.domain.stores.DataStore
import kotlinx.coroutines.delay

class EmailsRepository(
    private val remoteDataStore: DataStore
) : DataStore {

    companion object {
        fun newInstance(): EmailsRepository {
            return EmailsRepository(
                HttpDataStore.newInstance()
            )
        }
    }

    override suspend fun emails(page: Int, limit: Int): Result<List<Email>> {
        delay(5000)
        return remoteDataStore.emails(page, limit)
    }
}