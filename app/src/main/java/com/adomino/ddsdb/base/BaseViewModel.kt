package com.adomino.ddsdb.base

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  override fun onCleared() {
    super.onCleared()
    compositeDisposable.clear()
  }

  private fun Disposable.disposableOnClear() {
    compositeDisposable.add(this)
  }

  fun <T> Observable<T>.execute(success: T.() -> Unit, error: (Throwable) -> Unit) {
    this.subscribe({ response ->
      success.invoke(response)
    }, { t ->
      error.invoke(t)
    }
    ).disposableOnClear()
  }

  fun <T> Single<T>.execute(success: T.() -> Unit, error: (Throwable) -> Unit) {
    this.toObservable().execute(success, error)
  }
}