package ddsdb.vn.lcd.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    supportActionBar?.let { actionBar ->
      onActionBarConfiguration(actionBar)
    }
  }

  abstract fun onActionBarConfiguration(actionBar: ActionBar)

  @IdRes
  abstract fun viewMainId(): Int

  protected fun attachFragment(frag: BaseFragment) {
    supportFragmentManager
      .beginTransaction()
      .replace(viewMainId(), frag)
      .commit()
  }

  protected fun attachFragment(frag: BaseFragment, fragTag: String) {
    supportFragmentManager
      .beginTransaction()
      .add(viewMainId(), frag, fragTag)
      .commit()
  }

  fun setActionBarTitle(title: String, subTitle: String = "") {
    supportActionBar?.let { actionbar ->
      actionbar.title = title
      if (subTitle.isNotEmpty()) {
        actionbar.subtitle = subTitle
      }
    }
  }
}