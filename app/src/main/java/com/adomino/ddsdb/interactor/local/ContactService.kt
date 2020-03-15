package com.adomino.ddsdb.interactor.local

import com.adomino.ddsdb.data.ContactInfo
import com.adomino.ddsdb.helper.contact.ContactTask
import io.reactivex.Single
import javax.inject.Inject

class ContactService @Inject constructor(
  private val contactTask: ContactTask
) : LocalService.Contact {
  override fun fetchContactList(): Single<List<ContactInfo>> {
    return Single.fromCallable {
      contactTask.load()
    }
  }

  override fun updateContact(
    contacts: List<ContactInfo>
  ): Single<Boolean> {
    return Single.fromCallable {
      contacts.forEach { contact ->
        val result = contactTask.update(contact)
        if (!result) {
          return@fromCallable false
        }
      }
      true
    }
  }
}