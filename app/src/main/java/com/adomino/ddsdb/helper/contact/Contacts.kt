package com.adomino.ddsdb.helper.contact

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import com.adomino.ddsdb.data.ContactInfo

class Contacts(private val resolver: ContentResolver) : ContactTask {

  override fun load(): List<ContactInfo> {
    val cursor = resolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null,
        null,
        "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} asc"
    )
    return cursor.use { cs ->
      val css: Cursor = cs ?: throw NullPointerException("cursor is null")
      val result: MutableList<ContactInfo> = mutableListOf()
      if (css.moveToFirst()) {
        do {
          val id = css.getInt(
              css.getColumnIndex(
                  ContactsContract.CommonDataKinds.Phone.CONTACT_ID
              )
          )
          val displayName = css.getString(
              css.getColumnIndex(
                  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
              )
          )
          val phoneNumber = css.getString(
              css.getColumnIndex(
                  ContactsContract.CommonDataKinds.Phone.NUMBER
              )
          )
          val contactInfo = ContactInfo(
              id = id,
              displayName = displayName,
              phoneNumber = phoneNumber
          )
          result.add(contactInfo)
        } while (css.moveToNext())
      }
      result.toList()
    }
  }

  override fun insert(contactInfo: ContactInfo): Long {
    return 0
  }

  override fun update(contactInfo: ContactInfo): Boolean {
    val ops = arrayListOf<ContentProviderOperation>()
    val whereClause = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"
    val params = arrayOf(contactInfo.id.toString())
    ops.add(
        ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
            .withSelection(whereClause, params)
            .withValue(ContactsContract.CommonDataKinds.Phone.DATA, contactInfo.phoneNumber)
            .build()
    )
    resolver.applyBatch(ContactsContract.AUTHORITY, ops)
    return true
  }

  override fun remove(contactInfo: ContactInfo): ContactInfo {
    val ops = arrayListOf<ContentProviderOperation>()
    val whereClause = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"
    val params = arrayOf(
        contactInfo.id.toString()
    )
    ops.add(
        ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
            .withSelection(whereClause, params)
            .build()
    )
    resolver.applyBatch(ContactsContract.AUTHORITY, ops)
    return contactInfo
  }
}