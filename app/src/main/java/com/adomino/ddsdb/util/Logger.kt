package com.adomino.ddsdb.util

import android.util.Log

object Logger {

  fun d(message: String) {
    Log.d("ContactUtilities", message)
  }

  fun i(message: String) {
    Log.i("ContactUtilities", message)
  }

  fun e(message: String) {
    Log.e("ContactUtilities", message)
  }
}