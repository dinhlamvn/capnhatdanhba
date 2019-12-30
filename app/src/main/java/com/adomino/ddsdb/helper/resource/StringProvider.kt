package ddsdb.vn.lcd.helper.resource

import android.content.Context

class StringProvider(
  private val context: Context
) : ResourceProvider.StringResourceProvider {

  override fun get(resId: Int): String {
    return context.getString(resId)
  }

  override fun get(resId: Int, vararg args: Any): String {
    return context.getString(resId, args)
  }
}