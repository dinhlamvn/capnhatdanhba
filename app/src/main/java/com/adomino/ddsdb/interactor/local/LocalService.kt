package com.adomino.ddsdb.interactor.local

import com.adomino.ddsdb.data.ContactInfo
import io.reactivex.Single

interface LocalService {

  interface Contact {
    fun fetchContactList(): Single<List<ContactInfo>>
    fun updateContact(
      contacts: List<ContactInfo>
    ): Single<Boolean>
  }
}