package io.ybiletskyi.domain.stores.local

import androidx.room.*
import io.ybiletskyi.domain.Email

@Dao
internal abstract class EmailsDao {
    @Insert
    abstract suspend fun add(email: Email)

    @Insert
    abstract suspend fun add(emails: Collection<Email>)

    @Delete
    abstract suspend fun delete(email: Email)

    @Transaction
    open suspend fun update(emails: Collection<Email>) {
        emails.forEach { email ->
            delete(email)
            add(email)
        }
    }

    @Query("SELECT * FROM email WHERE is_deleted LIKE :isDeleted ORDER BY time DESC LIMIT :pageLimit OFFSET :page")
    abstract suspend fun emails(page: Int, pageLimit: Int, isDeleted: Int): List<Email>
}