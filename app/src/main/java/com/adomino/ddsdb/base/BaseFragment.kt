package com.adomino.ddsdb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.adomino.ddsdb.R
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

  companion object {
    const val KEY_ARGS = "arguments"
  }

  abstract fun getLayoutResource(): Int

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    getBaseActivity().setActionBarTitle(getString(R.string.app_name))
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(getLayoutResource(), container, false)
  }

  fun getBaseActivity(): BaseActivity {
    return activity as BaseActivity
  }

  fun attachTo(activity: AppCompatActivity, @IdRes containerId: Int) {
    activity.supportFragmentManager
      .beginTransaction()
      .add(containerId, this)
      .commit()
  }
}