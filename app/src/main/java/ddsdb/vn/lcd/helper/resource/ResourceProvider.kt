package ddsdb.vn.lcd.helper.resource

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ResourceProvider<out T> {

  interface StringResourceProvider : ResourceProvider<String> {
    fun get(@StringRes resId: Int): String
    fun get(@StringRes resId: Int, vararg args: Any): String
  }

  interface DrawableResourceProvider : ResourceProvider<Drawable> {
    fun get(@DrawableRes resId: Int): Drawable?
  }
}