package com.adomino.ddsdb.util

import android.content.Context
import android.os.Looper
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.annotation.MenuRes
import androidx.annotation.NonNull
import androidx.appcompat.widget.PopupMenu

object UIHelper {

  @MainThread
  fun showPopupMenu(
    uiContext: Context,
    anchorView: View,
    @MenuRes menuRes: Int,
    popupListener: (MenuItem) -> Boolean
  ) {
    check(Looper.myLooper() == Looper.getMainLooper()) {
      "Just call show popup menu on Main Thread."
    }
    PopupMenu(uiContext, anchorView).apply {
          menuInflater.inflate(menuRes, this.menu)
          setOnMenuItemClickListener { menuItem ->
            popupListener.invoke(menuItem)
          }
        }
        .show()
  }

  @MainThread
  fun showToast(
    uiContext: Context,
    @NonNull message: CharSequence,
    mode: Int = Toast.LENGTH_SHORT,
    gravity: Int = Gravity.BOTTOM
  ) {
    check(Looper.myLooper() == Looper.getMainLooper()) {
      "Just show toast on Main Thread."
    }
    Toast.makeText(uiContext, message, mode)
        .apply {
          setGravity(gravity, 0, 0)
        }
        .show()
  }
}