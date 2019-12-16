package android.vn.lcd.extensions

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

fun String.highLightOldPhoneNumber(): Spannable {
  val strBuilder: StringBuilder = StringBuilder(this)
  strBuilder.insert(0, "(")
  strBuilder.insert(5, ")")
  val builder = SpannableStringBuilder(strBuilder.toString())
  builder.setSpan(
    ForegroundColorSpan(Color.RED),
    1,
    this.length - 6,
    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
  )
  builder.setSpan(
    StyleSpan(Typeface.BOLD),
    1,
    this.length - 6,
    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
  )
  return builder
}

fun String.highLightNewPhoneNumber(): Spannable {
  val strBuilder: StringBuilder = StringBuilder(this)
  strBuilder.insert(0, "(")
  strBuilder.insert(4, ")")
  val builder = SpannableStringBuilder(strBuilder.toString())
  builder.setSpan(
    ForegroundColorSpan(Color.BLUE),
    1,
    this.length - 6,
    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
  )
  builder.setSpan(
    StyleSpan(Typeface.BOLD),
    1,
    this.length - 6,
    Spannable.SPAN_EXCLUSIVE_INCLUSIVE
  )
  return builder
}