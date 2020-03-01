package com.adomino.ddsdb.ui.sample

import android.view.View
import android.widget.TextView
import com.adomino.ddsdb.R
import com.adomino.ddsdb.recyclerview.XViewHolder

class SampleViewHolder(view: View) : XViewHolder<SampleModel>(view) {

  private val textView: TextView = view.findViewById(R.id.textView)

  override fun bind(model: SampleModel) {
    textView.text = model.name
  }
}