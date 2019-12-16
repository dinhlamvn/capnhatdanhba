package android.vn.lcd.helper

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.provider.ContactsContract
import android.vn.lcd.data.ContactUpdateInfo
import timber.log.Timber

const val RESULT_SUCCESS = -1

object ContactHelper {

  private fun changePhoneNumber(
    resolver: ContentResolver,
    contactId: Int,
    newPhoneNumber: String
  ): Int {
    val ops = arrayListOf<ContentProviderOperation>()

    val whereClause = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ? and " +
      "${ContactsContract.Data.MIMETYPE} = ?"

    val params = arrayOf(
      contactId.toString(),
      ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
    )

    ops.add(ContentProviderOperation.newUpdate(ContactsContract.RawContacts.CONTENT_URI)
      .withSelection(whereClause, params)
      .withValue(ContactsContract.CommonDataKinds.Phone.DATA, newPhoneNumber)
      .build())

    runCatching {
      Timber.d("Change $contactId to $newPhoneNumber")
      resolver.applyBatch(ContactsContract.AUTHORITY, ops)
    }.onSuccess {
      Timber.d("Change $contactId to $newPhoneNumber success")
      return RESULT_SUCCESS
    }
    Timber.d("Change $contactId to $newPhoneNumber fail")
    return contactId
  }

  fun changeListPhoneNumber(
    resolver: ContentResolver,
    contactUpdateInfoList: List<ContactUpdateInfo>
  ): Array<Int> {
    val arrayList = arrayListOf<Int>()
    contactUpdateInfoList.forEach { contactUpdateInfoItem ->
      val result = changePhoneNumber(
        resolver,
        contactUpdateInfoItem.contactInfo.id,
        contactUpdateInfoItem.newPhoneNumber
      )
      if (result > 0) {
        arrayList.add(result)
      }
    }
    return arrayList.toTypedArray()
  }

  fun removeContact(
    resolver: ContentResolver,
    contactId: Int
  ): Int {
    val ops = arrayListOf<ContentProviderOperation>()

    val whereClause = "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?"

    val params = arrayOf(
      contactId.toString()
    )

    ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
      .withSelection(whereClause, params)
      .build())

    runCatching {
      resolver.applyBatch(ContactsContract.AUTHORITY, ops)
    }.onSuccess {
      return RESULT_SUCCESS
    }
    return contactId
  }
}