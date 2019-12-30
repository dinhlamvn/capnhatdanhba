package com.adomino.ddsdb.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }

  fun Disposable.disposableOnClear() {
    compositeDisposable.add(this)
  }
}