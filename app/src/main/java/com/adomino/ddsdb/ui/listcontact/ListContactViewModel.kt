package com.adomino.ddsdb.ui.listcontact

import androidx.lifecycle.LiveData
import com.adomino.ddsdb.base.BaseViewModel
import com.adomino.ddsdb.common.SingleLiveEvent
import com.adomino.ddsdb.data.ContactUpdateInfo
import com.adomino.ddsdb.extensions.isInvalidHeadNumber
import com.adomino.ddsdb.extensions.mapToNewPhoneNumber
import com.adomino.ddsdb.interactor.local.LocalService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListContactViewModel @Inject constructor(
  private val services: LocalService.Contact
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

  init {
    loadContactList()
  }

  private fun startLoadingUpdate() {
    _loadingUpdateContact.submitChange(true)
  }

  fun updateContact() {
    startLoadingUpdate()
    val currentList = contactList.value ?: return
    services.updateContact(
            currentList.filter { contactUpdateInfo ->
                  contactUpdateInfo.contactInfo.phoneNumber.isInvalidHeadNumber()
                }
                .map { contactUpdateInfo ->
                  val contactInfo = contactUpdateInfo.contactInfo
                  contactInfo.copy(phoneNumber = contactInfo.phoneNumber.mapToNewPhoneNumber())
                }
        )
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .execute(success = {
          syncData(_updateContact, this)
        }, error = {
          syncData(_updateContact, false)
        })
  }

  fun loadContactList() {
    startLoading()
    services.fetchContactList()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .execute {
          syncData(
              _contactList,
              this.map { contactInfo -> ContactUpdateInfo(contactInfo, "") }
          )
        }
  }
}