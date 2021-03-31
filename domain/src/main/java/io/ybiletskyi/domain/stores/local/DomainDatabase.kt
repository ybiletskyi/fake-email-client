package io.ybiletskyi.domain.stores.local

import androidx.room.Database
import androidx.room.RoomDatabase
import io.ybiletskyi.domain.Email

@Database(entities = [Email::class], version = 1)
internal abstract class DomainDatabase: RoomDatabase() {
    abstract fun emailsDao(): EmailsDao
}