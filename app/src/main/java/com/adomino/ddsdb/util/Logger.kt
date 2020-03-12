package com.adomino.ddsdb.util

import android.util.Log

object Logger {

  private const val TAG = "ContactUtilApp"

  private var enableLog: Boolean = false

  fun d(message: String) {
    if (enableLog) {
      Log.d(TAG, message)
    }
  }

  fun i(message: String) {
    if (enableLog) {
      Log.i(TAG, message)
    }
  }

  fun e(message: String) {
    if (enableLog) {
      Log.e(TAG, message)
    }
  }
}