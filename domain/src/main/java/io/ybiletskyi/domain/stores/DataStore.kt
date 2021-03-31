package io.ybiletskyi.domain.stores

import io.ybiletskyi.domain.Email
import io.ybiletskyi.domain.Result

interface DataStore {
    /** load email from store **/
    suspend fun email(id: Int): Result<Email>
    /** load emails from store **/
    suspend fun emails(page: Int, limit: Int, isDeleted: Boolean): Result<List<Email>>
    /** save emails in store **/
    suspend fun saveEmails(emails: Collection<Email>): Result<Unit>
    /** update emails in store **/
    suspend fun updateEmails(emails: Collection<Email>): Result<Unit>
}