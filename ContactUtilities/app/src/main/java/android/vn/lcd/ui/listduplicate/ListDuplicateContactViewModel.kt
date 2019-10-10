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

    fun loadContactList() {
        LoadHelper.loadListContact(contentResolver)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _showLoading.value = LoadingInfo(isShow = true)
                }
                .doOnComplete {
                    _showLoading.value = LoadingInfo(isShow = false)
                }
                .map { result -> result.filterNotAlone() }
                .subscribe({ result ->
                    _contactList.value = result
                }, { error ->
                    _contactLoadError.value = error.message
                })
                .disposableOnClear()
    }

    fun refreshList() {
        _contactList.value = listOf()
        loadContactList()
    }
}