package com.adomino.ddsdb.database.helper

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adomino.ddsdb.database.entity.Contact
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable

@Dao
interface ContactDao {

  @Query("select uid, name, phone_number from contacts order by name")
  fun list(): Observable<List<Contact>>

  @Query("select uid, name, phone_number from contacts where uid in (:uid)")
  fun getById(uid: Int): Maybe<Contact>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(contact: Contact): Completable

  @Delete
  fun delete(contact: Contact): Completable
}