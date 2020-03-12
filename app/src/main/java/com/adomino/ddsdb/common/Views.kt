package com.adomino.ddsdb.common

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun <T : View> View.bindView(@IdRes id: Int): Lazy<T> {
  check(id != 0) { "Id must not be zero" }
  return lazy { findViewById<T>(id) }
}

fun <T : View> Activity.bindView(@IdRes id: Int): Lazy<T> {
  check(id != 0) { "Id must not be zero" }
  return lazy { findViewById<T>(id) }
}

fun <T : View> Fragment.bindView(@IdRes id: Int): Lazy<T> {
  check(id != 0 ) { "Id must not be zero" }
  return lazy { view!!.findViewById<T>(id) }
}