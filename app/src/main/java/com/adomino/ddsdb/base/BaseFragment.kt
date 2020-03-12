package com.adomino.ddsdb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adomino.ddsdb.common.Constant
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

  abstract fun layout(): Int

  abstract fun onInitUI(view: View)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layout(), container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onInitUI(view)
  }

  fun args(): Lazy<FragmentArgs> {
    return lazy {
      checkNotNull(arguments) { "Argument is null value." }.getParcelable(
          Constant.FRAGMENT_ARGS) as FragmentArgs
    }
  }
}