package android.vn.lcd.ui.listduplicate

import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.vn.lcd.base.BaseViewModel
import android.vn.lcd.data.ContactInfo
import android.vn.lcd.data.LoadingInfo
import android.vn.lcd.data.PercentLoadingInfo
import android.vn.lcd.extensions.filterNotAlone
import android.vn.lcd.helper.ContactHelper
import android.vn.lcd.helper.LoadHelper
import android.vn.lcd.helper.RESULT_SUCCESS
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
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

    private val _removeContactList = MutableLiveData<List<ContactInfo>>()
    val removeContactList: LiveData<List<ContactInfo>>
        get() = _removeContactList

    private val _percentLoading = MutableLiveData<PercentLoadingInfo>()
    val percentLoading : LiveData<PercentLoadingInfo>
        get() = _percentLoading

    init {
        _showLoading.value = LoadingInfo()
        _removeContactList.value = mutableListOf()
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

    fun removeContactList() {
        _removeContactList.value?.let { list ->
            val total = list.size
            var deleted = 0
            Observable.fromIterable(list)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete {
                        _percentLoading.value = PercentLoadingInfo(isShow = false)
                        loadContactList()
                    }
                    .subscribe({ contactInfo ->
                        val result = ContactHelper.removeContact(contentResolver, contactInfo.id)
                        if (result == RESULT_SUCCESS) {
                            deleted = deleted.plus(1)
                            _percentLoading.value = PercentLoadingInfo(
                                    "Deleting...",
                                    percent = deleted,
                                    isShow = true,
                                    total = total
                            )
                        }
                    }, {error ->
                        Timber.d("Error when remove $error")
                    }).disposableOnClear()
        }
    }

    fun addContactToRemove(contactInfo: ContactInfo) {
        val currentList = _removeContactList.value as MutableList
        currentList.add(contactInfo)
        _removeContactList.value = currentList
    }

    fun removeContactFromRemoveList(contactInfo: ContactInfo) {
        val currentList = _removeContactList.value as MutableList
        currentList.remove(contactInfo)
        _removeContactList.value = currentList
    }

    fun refreshList() {
        _contactList.value = listOf()
        loadContactList()
    }
}