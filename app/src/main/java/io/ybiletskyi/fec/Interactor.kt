package io.ybiletskyi.fec

import io.ybiletskyi.domain.Email
import io.ybiletskyi.domain.EmailsRepository
import io.ybiletskyi.domain.Result
import io.ybiletskyi.domain.stores.DataStore

/**
 * The emails repository is passive model, as we need to get updates of stores changing we need active model.
 * Interactor is active model that wrapping logic of acting with repository.
 */
object Interactor : DataStore {

    private val listeners = mutableListOf<UpdatesObserver>()
    private val repository = EmailsRepository.newInstance(App.context)

    fun addObserver(observer: UpdatesObserver) {
        listeners.add(observer)
    }

    fun removeObserver(observer: UpdatesObserver) {
        listeners.remove(observer)
    }

    override suspend fun email(id: Int): Result<Email> {
        return repository.email(id)
    }

    override suspend fun emails(page: Int, limit: Int, isDeleted: Boolean): Result<List<Email>> {
        return repository.emails(page, limit, isDeleted)
    }

    override suspend fun saveEmails(emails: Collection<Email>): Result<Unit> {
        return repository.saveEmails(emails).also { result ->
            if (result is Result.Success) {
                listeners.forEach { listener -> listener.onDataAdded(emails) }
            }
        }
    }

    override suspend fun updateEmails(emails: Collection<Email>): Result<Unit> {
        return repository.updateEmails(emails).also { result ->
            if (result is Result.Success) {
                listeners.forEach { listener -> listener.onDataUpdated(emails) }
            }
        }
    }

    interface UpdatesObserver {
        fun onDataAdded(data: Collection<Email>)
        fun onDataUpdated(data: Collection<Email>)
    }
}