package android.vn.lcd.extensions

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

fun String.highLightOldPhoneNumber(): Spannable {
    val builder = SpannableStringBuilder(this)
    builder.setSpan(
            ForegroundColorSpan(Color.RED),
            0,
            this.length - 7,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    builder.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            this.length - 7,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    return builder
}

fun String.highLightNewPhoneNumber(): Spannable {
    val builder = SpannableStringBuilder(this)
    builder.setSpan(
            ForegroundColorSpan(Color.BLUE),
            0,
            this.length - 6,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    builder.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            this.length - 7,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    return builder
}