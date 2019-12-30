package com.adomino.ddsdb.helper.resource

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class DrawableProvider(
  private val context: Context
) : ResourceProvider.DrawableResourceProvider {
  override fun get(resId: Int): Drawable? {
    return ContextCompat.getDrawable(context, resId)
  }
}