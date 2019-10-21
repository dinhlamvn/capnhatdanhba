package android.vn.lcd.extensions

import android.vn.lcd.data.ContactInfo
import io.reactivex.Observable

fun <E> List<E>.toObservable(): Observable<List<E>> =
        Observable.just(this)

fun List<ContactInfo>.filterNotAlone(): List<ContactInfo> =
        filter { contact ->
            count { it.phoneNumber phoneNumberEqualsTo contact.phoneNumber } > 1
        }

fun main() {
    val contactInfo1 = ContactInfo(id = 1, phoneNumber = "01663706226", displayName = "a")
    val contactInfo2 = ContactInfo(id = 2, phoneNumber = "01663706227", displayName = "b")
    val contactInfo3 = ContactInfo(id = 3, phoneNumber = "01663706228", displayName = "c")
    val contactInfo4 = ContactInfo(id = 4, phoneNumber = "+84 166 370 6226", displayName = "d")
    val list = listOf(contactInfo1, contactInfo2, contactInfo3, contactInfo4)
    print(list.filterNotAlone())
}