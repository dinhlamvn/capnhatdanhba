package com.adomino.ddsdb.ui.listcontact.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adomino.ddsdb.R
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.recyclerview.XViewHolder
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUpdateUIModel

class UpdateLoadingViewHolder(view: View) : XViewHolder<ContactUpdateUIModel>(view) {

  companion object {
    fun create(viewGroup: ViewGroup): UpdateLoadingViewHolder {
      return LayoutInflater.from(viewGroup.context)
          .inflate(R.layout.loading, viewGroup, false)
          .run {
            UpdateLoadingViewHolder(this)
          }
    }
  }

  private val tvLoading: TextView by view.bindView(R.id.tvLoading)

  override fun bind(model: ContactUpdateUIModel) {
    tvLoading.text = model.message
  }
}