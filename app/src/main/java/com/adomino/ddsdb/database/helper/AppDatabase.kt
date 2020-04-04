package com.adomino.ddsdb.database.helper

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adomino.ddsdb.database.entity.Contact

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun contactDao(): ContactDao
}