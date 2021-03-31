package io.ybiletskyi.domain

import android.content.Context
import io.ybiletskyi.domain.stores.http.HttpDataStore
import io.ybiletskyi.domain.stores.DataStore
import io.ybiletskyi.domain.stores.local.DbStore
import kotlinx.coroutines.delay

class EmailsRepository(
    private val remoteDataStore: DataStore,
    private val localDataStore: DataStore,
) : DataStore {

    companion object {
        private const val DELAY_TIME = 2000L

        fun newInstance(context: Context): EmailsRepository {
            return EmailsRepository(
                HttpDataStore.newInstance(),
                DbStore.newInstance(context)
            )
        }
    }

    override suspend fun emails(page: Int, limit: Int, isDeleted: Boolean): Result<List<Email>> {
        // simulate delay
        delay(DELAY_TIME)
        // as remote data store doesn't support modifications, local changes have higher priority than remote.
        // so repository firstly load data from local storage. if data is not exist in the local store try to load from remote store
        val localResult = localDataStore.emails(page, limit, isDeleted)
        val isLocalCallSuccess = (localResult as? Result.Success)?.data?.isNotEmpty() == true
        return when (isLocalCallSuccess) {
            true -> localResult
            false -> remoteDataStore.emails(page, limit, isDeleted).apply {
                // if remote store returned data -- save it to local storage
                (this as? Result.Success)?.data?.let { newEmails ->
                    localDataStore.saveEmails(newEmails)
                }
            }
        }
    }

    override suspend fun saveEmails(emails: Collection<Email>): Result<Unit> {
        // simulate delay
        delay(DELAY_TIME)
        // remote data store doesn't support write operation, so changes save only locally
        return localDataStore.saveEmails(emails)
    }

    override suspend fun updateEmails(emails: Collection<Email>): Result<Unit> {
        // simulate delay
        delay(DELAY_TIME)
        // remote data store doesn't support update operation, so changes save only locally
        return localDataStore.updateEmails(emails)
    }
}