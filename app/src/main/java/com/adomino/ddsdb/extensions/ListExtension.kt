package com.adomino.ddsdb.extensions

import com.adomino.ddsdb.data.ContactInfo
import io.reactivex.Observable

fun <E> List<E>.toObservable(): Observable<List<E>> =
  Observable.just(this)

fun List<ContactInfo>.filterNotAlone(): List<ContactInfo> =
  filter { contact ->
    count { it.phoneNumber phoneNumberEqualsTo contact.phoneNumber } > 1
  }