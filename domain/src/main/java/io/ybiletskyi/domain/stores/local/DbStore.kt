package io.ybiletskyi.domain.stores.local

import android.content.Context
import androidx.room.Room
import io.ybiletskyi.domain.Email
import io.ybiletskyi.domain.Result
import io.ybiletskyi.domain.stores.DataStore

internal class DbStore(
    private val dbStore: DomainDatabase
): DataStore {

    companion object {
        fun newInstance(context: Context): DbStore {
            val database = Room.databaseBuilder(
                context,
                DomainDatabase::class.java,
                "emails-database"
            ).build()

            return DbStore(database)
        }
    }

    override suspend fun email(id: Int): Result<Email> {
        return try {
            val dao = dbStore.emailsDao()
            val emails = dao.email(id)
            Result.Success(emails)

        } catch (e: Throwable) {
            Result.Error(e.message)
        }
    }

    override suspend fun emails(page: Int, limit: Int, isDeleted: Boolean): Result<List<Email>> {
        return try {
            val dao = dbStore.emailsDao()
            val emails = dao.emails(page, limit, if (isDeleted) 1 else 0)
            Result.Success(emails)

        } catch (e: Throwable) {
            Result.Error(e.message)
        }
    }

    override suspend fun saveEmails(emails: Collection<Email>): Result<Unit> {
        return try {
            val dao = dbStore.emailsDao()
            dao.add(emails)
            Result.Success(Unit)

        } catch (e: Throwable) {
            Result.Error(e.message)
        }
    }

    override suspend fun updateEmails(emails: Collection<Email>): Result<Unit> {
        return try {
            val dao = dbStore.emailsDao()
            dao.update(emails)
            Result.Success(Unit)

        } catch (e: Throwable) {
            Result.Error(e.message)
        }
    }
}