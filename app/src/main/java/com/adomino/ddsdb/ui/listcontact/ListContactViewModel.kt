package com.adomino.ddsdb.ui.listcontact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adomino.ddsdb.base.BaseViewModel
import com.adomino.ddsdb.data.ContactUpdateInfo
import com.adomino.ddsdb.data.LoadingInfo
import com.adomino.ddsdb.extensions.isInvalidHeadNumber
import com.adomino.ddsdb.extensions.mapToNewPhoneNumber
import com.adomino.ddsdb.helper.contact.ContactTask
import com.adomino.ddsdb.util.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListContactViewModel @Inject constructor(
  private val contactTask: ContactTask
) : BaseViewModel() {

  private val _contactList = MutableLiveData<List<ContactUpdateInfo>>()
  val contactList: LiveData<List<ContactUpdateInfo>>
    get() = _contactList

  private val _contactLoadError = MutableLiveData<String>()
  val contactLoadError: LiveData<String>
    get() = _contactLoadError

  private val _showLoading = MutableLiveData<LoadingInfo>()
  val showLoading: LiveData<LoadingInfo>
    get() = _showLoading

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
        .subscribe { isUpdateSuccess ->
          if (isUpdateSuccess) {
            Logger.d("Update $info success")
          } else {
            Logger.d("Update $info fail")
          }
        }.disposableOnClear()
    }
  }

  private fun loadContactList() {
    contactTask.load()
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())
      .map { list -> list.filter { contactInfo -> contactInfo.phoneNumber.isInvalidHeadNumber() } }
      .map { list -> list.map { contact -> ContactUpdateInfo(contact, contact.phoneNumber.mapToNewPhoneNumber()) } }
      .subscribe { result ->
        _contactList.value = result
      }.disposableOnClear()
  }

  fun refreshList() {
    _contactList.value = listOf()
    loadContactList()
  }
}