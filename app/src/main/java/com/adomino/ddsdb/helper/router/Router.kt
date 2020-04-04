package com.adomino.ddsdb.helper.router

import android.content.Context

interface Router {
  fun startCall(context: Context, phoneNumber: String)
}