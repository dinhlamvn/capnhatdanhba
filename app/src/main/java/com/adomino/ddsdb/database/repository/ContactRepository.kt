package com.adomino.ddsdb.database.repository

import android.content.Context
import androidx.room.Room
import com.adomino.ddsdb.data.ContactInfo
import com.adomino.ddsdb.database.entity.Contact
import com.adomino.ddsdb.database.helper.AppDatabase
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ContactRepository @Inject constructor(
  context: Context
) {

  private val database: AppDatabase = Room.databaseBuilder(
          context, AppDatabase::class.java, "contactdb"
      )
      .build()

  fun insert(contactInfo: ContactInfo): Completable {
    return database.contactDao()
        .insert(
            Contact(
                uid = contactInfo.id, name = contactInfo.displayName,
                phoneNumber = contactInfo.phoneNumber
            )
        )
  }

  fun fetch(): Observable<List<ContactInfo>> {
    return database.contactDao()
        .list()
        .map { source ->
          source.map {
            ContactInfo(
                id = it.uid, displayName = it.name.orEmpty(), phoneNumber = it.phoneNumber.orEmpty()
            )
          }
        }
  }
}