package com.adomino.ddsdb.ui.listcontact

import androidx.lifecycle.LiveData
import com.adomino.ddsdb.base.BaseViewModel
import com.adomino.ddsdb.common.SingleLiveEvent
import com.adomino.ddsdb.data.ContactUpdateInfo
import com.adomino.ddsdb.interactor.local.LocalService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
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

  init {
    loadContactList()
  }

  fun updateContact() {
    _updateContact.submitChange(true)
  }

  fun loadContactList() {
    services.fetchContactList()
        .delay(1, TimeUnit.SECONDS)
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