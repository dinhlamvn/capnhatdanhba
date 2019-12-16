package android.vn.lcd.ui.listcontact

import android.content.ContentResolver
import android.vn.lcd.base.BaseViewModel
import android.vn.lcd.data.ContactUpdateInfo
import android.vn.lcd.data.LoadingInfo
import android.vn.lcd.extensions.isInvalidHeadNumber
import android.vn.lcd.extensions.mapToNewPhoneNumber
import android.vn.lcd.helper.ContactHelper
import android.vn.lcd.helper.LoadHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListContactViewModel(private val contentResolver: ContentResolver) : BaseViewModel() {

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
    contactList.value?.let { contactInfoList ->
      Single.fromCallable {
        ContactHelper.changeListPhoneNumber(contentResolver, contactInfoList)
      }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { _showLoading.value = LoadingInfo(message = "Converting...", isShow = true) }
        .doOnSuccess { _showLoading.value = LoadingInfo(isShow = false) }
        .subscribe { response, error ->
          if (response.isNotEmpty()) {
            _contactLoadError.value = "Update error ${error.localizedMessage}"
          } else {
            loadContactList()
          }
        }.disposableOnClear()
    }
  }

  private fun loadContactList() {
    LoadHelper.loadListContact(contentResolver)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnSubscribe {
        _showLoading.value = LoadingInfo(isShow = true)
      }
      .map { list -> list.filter { contact -> contact.phoneNumber.isInvalidHeadNumber() } }
      .map { list -> list.map { contact -> ContactUpdateInfo(contact, contact.phoneNumber.mapToNewPhoneNumber()) } }
      .subscribe({ result ->
        _contactList.value = result
        _showLoading.value = LoadingInfo(isShow = false)
      }, { error ->
        _contactLoadError.value = error.message
      }).disposableOnClear()
  }

  fun refreshList() {
    _contactList.value = listOf()
    loadContactList()
  }
}