package android.vn.lcd.helper

import android.content.ContentResolver
import android.provider.ContactsContract
import android.vn.lcd.data.ContactInfo
import android.vn.lcd.extensions.toObservable
import io.reactivex.Observable

open class LoadHelper {

  companion object {

    fun loadListContact(resolver: ContentResolver): Observable<List<ContactInfo>> {

      val resultList = mutableListOf<ContactInfo>()

      val contactCursor = resolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
          ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
          ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
          ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null,
        null,
        "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} asc")

      contactCursor?.let { cursor ->
        if (cursor.moveToFirst()) {
          do {
            val id = cursor.getInt(cursor.getColumnIndex(
              ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            ))
            val displayName = cursor.getString(cursor.getColumnIndex(
              ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            ))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(
              ContactsContract.CommonDataKinds.Phone.NUMBER
            ))
            val contactInfo = ContactInfo(
              id = id,
              displayName = displayName,
              phoneNumber = phoneNumber
            )
            resultList.add(contactInfo)
          } while (cursor.moveToNext())
        }
      }

      contactCursor?.close()

      return resultList.toList().toObservable()
    }
  }
}