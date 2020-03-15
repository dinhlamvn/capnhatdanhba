package com.adomino.ddsdb.util

import android.content.Context
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.annotation.MainThread
import androidx.annotation.MenuRes
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
}