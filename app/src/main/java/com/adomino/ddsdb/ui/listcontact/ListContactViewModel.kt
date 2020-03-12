package com.adomino.ddsdb.ui.listcontact

import androidx.lifecycle.LiveData
import com.adomino.ddsdb.base.BaseViewModel
import com.adomino.ddsdb.common.SingleLiveEvent
import com.adomino.ddsdb.data.ContactUpdateInfo
import com.adomino.ddsdb.extensions.isInvalidHeadNumber
import com.adomino.ddsdb.extensions.mapToNewPhoneNumber
import com.adomino.ddsdb.helper.contact.ContactTask
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListContactViewModel @Inject constructor(
  private val contactTask: ContactTask
) : BaseViewModel() {

  private val _contactList = SingleLiveEvent<List<ContactUpdateInfo>>()
  val contactList: LiveData<List<ContactUpdateInfo>>
    get() = _contactList

  init {
    loadContactList()
  }

  fun updateContact() {
    val contactDataList = contactList.value ?: return
    contactDataList.map { updateInfo ->
      updateInfo.contactInfo
    }.forEach { info ->
      contactTask.update(info)
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .execute({

          }, {

          })
    }
  }

  private fun loadContactList() {
    contactTask.load()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .map { list -> list.filter { contactInfo -> contactInfo.phoneNumber.isInvalidHeadNumber() } }
        .map { list ->
          list.map { contact ->
            ContactUpdateInfo(contact, contact.phoneNumber.mapToNewPhoneNumber())
          }
        }
        .execute({
          _contactList.setValue(this)
        }, {

        })
  }
}