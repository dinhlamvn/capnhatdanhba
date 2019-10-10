package android.vn.lcd.helper

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import android.vn.lcd.data.ContactInfo
import android.vn.lcd.data.ContactUpdateInfo
import android.vn.lcd.extensions.mapToNewPhoneNumber

const val CONTACT_CHANGE_RESULT_SUCCESS = -1

object ContactHelper {

    private fun changePhoneNumber(
            resolver: ContentResolver,
            contactId: Int,
            newPhoneNumber: String
    ): Int {
        val ops = arrayListOf<ContentProviderOperation>()

        val whereClause = "${ContactsContract.CommonDataKinds.Phone._ID} = ? and " +
                "${ContactsContract.Data.MIMETYPE} = ?"

        val params = arrayOf(
                contactId.toString(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
        )

        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(whereClause, params)
                .withValue(ContactsContract.CommonDataKinds.Phone.DATA, newPhoneNumber)
                .build())

        runCatching {
            Log.d("ConvertNumber", "Change $contactId to $newPhoneNumber")
            resolver.applyBatch(ContactsContract.AUTHORITY, ops)
        }.onSuccess {
            Log.d("ConvertNumber", "Change $contactId to $newPhoneNumber success")
            return CONTACT_CHANGE_RESULT_SUCCESS
        }
        Log.d("ConvertNumber", "Change $contactId to $newPhoneNumber fail")
        return contactId
    }

    fun changeListPhoneNumber(
            resolver: ContentResolver,
            contactInfoList: List<ContactUpdateInfo>
    ): Array<Int> {
        val arrayList = arrayListOf<Int>()
        contactInfoList.forEach { contactUpdateInfoItem ->
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
}