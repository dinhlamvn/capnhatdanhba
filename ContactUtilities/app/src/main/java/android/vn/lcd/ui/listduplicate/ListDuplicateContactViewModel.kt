package android.vn.lcd.ui.listduplicate

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.vn.lcd.base.BaseViewModel
import android.vn.lcd.data.ContactInfo
import android.vn.lcd.data.LoadingInfo
import android.vn.lcd.extensions.filterNotAlone
import android.vn.lcd.helper.ContactHelper
import android.vn.lcd.helper.LoadHelper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ListDuplicateContactViewModel(private val contentResolver: ContentResolver) : BaseViewModel() {

    private val _contactList = MutableLiveData<List<ContactInfo>>()
    val contactList : LiveData<List<ContactInfo>>
        get() = _contactList

    private val _contactLoadError = MutableLiveData<String>()
    val contactLoadError : LiveData<String>
        get() = _contactLoadError

    private val _showLoading = MutableLiveData<LoadingInfo>()
    val showLoading : LiveData<LoadingInfo>
        get() = _showLoading

    init {
        _showLoading.value = LoadingInfo()
    }

    fun updateContact() {
        contactList.value?.let { contactInfoList ->
            val disposable = Single.fromCallable {
                ContactHelper.changeListPhoneNumber(contentResolver, contactInfoList)
            }.delay(5000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { _showLoading.value = LoadingInfo(title = "Converting...", isShow = true) }
                    .doOnSuccess { _showLoading.value = LoadingInfo(isShow = false) }
                    .subscribe { response, error ->
                        if (response.isNotEmpty()) {
                            _contactLoadError.value = "Update error ${error.localizedMessage}"
                        } else {
                            loadContactList()
                        }
                    }
            compositeDisposable.add(disposable)
        }
    }

    fun loadContactList() {
        val disposable = LoadHelper.loadListContact(contentResolver)
                .delay(5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _showLoading.value = LoadingInfo(title = "Converting...", isShow = true)
                }
                .doOnComplete {
                    _showLoading.value = LoadingInfo(isShow = false)
                }
                .subscribe({ result ->
                    _contactList.value = result.filterNotAlone()
                }, { error ->
                    _contactLoadError.value = error.message
                })
        compositeDisposable.add(disposable)
    }
}