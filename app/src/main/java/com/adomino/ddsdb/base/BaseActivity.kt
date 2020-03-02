package com.adomino.ddsdb.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.appcompat.app.ActionBar
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(viewId())

    supportActionBar?.let { actionBar ->
      onActionBarConfiguration(actionBar)
    }

    onInitUI()
  }

  open fun onActionBarConfiguration(actionBar: ActionBar) {
    
  }

  @LayoutRes
  abstract fun viewId(): Int

  @MainThread
  abstract fun onInitUI()

  fun setActionBarTitle(
    title: String,
    subTitle: String = ""
  ) {
    supportActionBar?.let { actionbar ->
      actionbar.title = title
      if (subTitle.isNotEmpty()) {
        actionbar.subtitle = subTitle
      }
    }
  }
}