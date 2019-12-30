package ddsdb.vn.lcd.helper.contact

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.provider.ContactsContract
import android.vn.lcd.data.ContactInfo
import android.vn.lcd.util.Logger
import io.reactivex.Single

class Contacts(private val resolver: ContentResolver) : ContactTask {

  override fun load(): Single<List<ContactInfo>> {
    return Single.fromCallable {
      resolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        arrayOf(
          ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
          ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
          ContactsContract.CommonDataKinds.Phone.NUMBER
        ),
        null,
        null,
        "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} asc")
    }.doOnSuccess { cs -> cs?.close() }
      .map { cs ->
        val result = mutableListOf<ContactInfo>()
        if (cs.moveToFirst()) {
          do {
            val id = cs.getInt(cs.getColumnIndex(
              ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            ))
            val displayName = cs.getString(cs.getColumnIndex(
              ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            ))
            val phoneNumber = cs.getString(cs.getColumnIndex(
              ContactsContract.CommonDataKinds.Phone.NUMBER
            ))
            val contactInfo = ContactInfo(
              id = id,
              displayName = displayName,
              phoneNumber = phoneNumber
            )
            result.add(contactInfo)
          } while (cs.moveToNext())
        }
        result.toList()
      }.onErrorReturnItem(emptyList())
  }

  override fun insert(contactInfo: ContactInfo): Single<Long> {
    return Single.just(0)
  }

  override fun update(contactInfo: ContactInfo): Single<Boolean> {
    return Single.fromCallable {
      val ops = arrayListOf<ContentProviderOperation>()
      val whereClause = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ? and " +
        "${ContactsContract.Data.MIMETYPE} = ?"
      val params = arrayOf(
        contactInfo.id.toString(),
        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
      )
      ops.add(ContentProviderOperation.newUpdate(ContactsContract.RawContacts.CONTENT_URI)
        .withSelection(whereClause, params)
        .withValue(ContactsContract.CommonDataKinds.Phone.DATA, contactInfo.phoneNumber)
        .build())
      resolver.applyBatch(ContactsContract.AUTHORITY, ops)
      true
    }.onErrorReturn {
      Logger.e("Error when try to update contact $contactInfo")
      false
    }
  }

  override fun remove(contactInfo: ContactInfo): Single<ContactInfo> {
    return Single.fromCallable {
      val ops = arrayListOf<ContentProviderOperation>()
      val whereClause = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"
      val params = arrayOf(
        contactInfo.id.toString()
      )
      ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
        .withSelection(whereClause, params)
        .build())
      resolver.applyBatch(ContactsContract.AUTHORITY, ops)
      contactInfo
    }.onErrorReturnItem(contactInfo)
  }
}