package com.adomino.ddsdb.ui.listcontact

import android.view.View
import android.widget.TextView
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.recyclerview.XViewHolder

class ListContactViewHolder(view: View) : XViewHolder<ContactUiModel>(view) {

  private val tvTextView: TextView by view.bindView(android.R.id.text1)

  override fun bind(model: ContactUiModel) {
    tvTextView.text = model.name
  }
}