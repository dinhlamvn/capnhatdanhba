package com.adomino.ddsdb.ui.listcontact.viewholder

import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.adomino.ddsdb.R
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.recyclerview.XViewHolder
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUpdateFailUiModel

class UpdateFailViewHolder(view: View) : XViewHolder<ContactUpdateFailUiModel>(view) {

  private val tvReload: TextView by view.bindView(R.id.tvReload)

  override fun bind(model: ContactUpdateFailUiModel) {
    tvReload.text = HtmlCompat.fromHtml(model.message, HtmlCompat.FROM_HTML_MODE_LEGACY)

    tvReload.setOnClickListener {
      model.onReload.invoke()
    }
  }
}