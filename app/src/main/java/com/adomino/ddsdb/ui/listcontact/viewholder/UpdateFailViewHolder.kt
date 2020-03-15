package com.adomino.ddsdb.ui.listcontact.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.adomino.ddsdb.R
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.recyclerview.XViewHolder
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUpdateFailUiModel

class UpdateFailViewHolder(view: View) : XViewHolder<ContactUpdateFailUiModel>(view) {

  companion object {
    fun create(viewGroup: ViewGroup): UpdateFailViewHolder {
      return LayoutInflater.from(viewGroup.context)
          .inflate(R.layout.contact_update_fail_view, viewGroup, false)
          .run { UpdateFailViewHolder(this) }
    }
  }

  private val tvReload: TextView by view.bindView(R.id.tvReload)

  override fun bind(model: ContactUpdateFailUiModel) {
    tvReload.text = HtmlCompat.fromHtml(model.message, HtmlCompat.FROM_HTML_MODE_LEGACY)

    tvReload.setOnClickListener {
      model.onReload.invoke()
    }
  }
}