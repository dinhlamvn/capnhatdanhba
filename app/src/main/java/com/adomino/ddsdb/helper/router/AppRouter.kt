package com.adomino.ddsdb.helper.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import javax.inject.Inject

class AppRouter @Inject constructor() : Router {

  override fun startCall(
    context: Context,
    phoneNumber: String
  ) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:$phoneNumber")
    context.startActivity(intent)
  }
}