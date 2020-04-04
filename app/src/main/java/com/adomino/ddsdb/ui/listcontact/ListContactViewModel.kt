package com.adomino.ddsdb.ui.listcontact

import androidx.lifecycle.LiveData
import com.adomino.ddsdb.base.BaseViewModel
import com.adomino.ddsdb.common.SingleLiveEvent
import com.adomino.ddsdb.data.ContactInfo
import com.adomino.ddsdb.data.ContactUpdateInfo
import com.adomino.ddsdb.database.repository.ContactRepository
import com.adomino.ddsdb.extensions.asStartZero
import com.adomino.ddsdb.extensions.hasNotAnySpace
import com.adomino.ddsdb.extensions.mapToNewPhoneNumber
import com.adomino.ddsdb.interactor.local.LocalService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListContactViewModel @Inject constructor(
  private val services: LocalService.Contact,
  private val repository: ContactRepository
) : BaseViewModel() {

  private val _contactList = SingleLiveEvent<List<ContactUpdateInfo>>()
  val contactList: LiveData<List<ContactUpdateInfo>>
    get() = _contactList

  private val _updateContact = SingleLiveEvent<Boolean>()
  val updateContact: LiveData<Boolean>
    get() = _updateContact

  private val _loadingUpdateContact = SingleLiveEvent<Boolean>()
  val loadingUpdateContact: LiveData<Boolean>
    get() = _loadingUpdateContact

  private fun startLoadingUpdate() {
    _loadingUpdateContact.submitChange(true)
  }

  fun updateContact() {
//    startLoadingUpdate()
//    val currentList = contactList.value ?: return
//    services.updateContact(
//            currentList.filter { contactUpdateInfo ->
//                  contactUpdateInfo.contactInfo.phoneNumber.isInvalidHeadNumber()
//                }
//                .map { contactUpdateInfo ->
//                  val contactInfo = contactUpdateInfo.contactInfo
//                  contactInfo.copy(phoneNumber = contactInfo.phoneNumber.mapToNewPhoneNumber())
//                }
//        )
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .execute(success = {
//          syncData(_updateContact, this)
//        }, error = {
//          syncData(_updateContact, false)
//        })
  }

  fun loadContactList() {
    startLoading()
    repository.fetch()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .execute {
          syncData(
              _contactList,
              this.map { ContactUpdateInfo(it, "") }
          )
        }
  }

  fun syncContactsToDatabase() {
    services.fetchContactList()
        .toObservable()
        .flatMap { contactList -> Observable.fromIterable(contactList) }
        .map { contactInfo: ContactInfo ->
          ContactInfo(
              contactInfo.id, contactInfo.displayName, contactInfo.phoneNumber.mapToNewPhoneNumber()
              .hasNotAnySpace()
              .asStartZero()
          )
        }
        .flatMap { contactInfo ->
          repository.insert(contactInfo)
              .toObservable<Unit>()
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnComplete {
          loadContactList()
        }
        .subscribe()
        .disposableOnClear()

  }
}