package com.adomino.ddsdb.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

  private val pending: AtomicBoolean = AtomicBoolean(false)

  override fun setValue(value: T) {
    pending.set(true)
    super.setValue(value)
  }

  override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
    super.observe(owner, Observer { value ->
      if (pending.compareAndSet(true, false)) {
        observer.onChanged(value)
      }
    })
  }

  fun submitChange(value: T) {
    setValue(value)
  }
}