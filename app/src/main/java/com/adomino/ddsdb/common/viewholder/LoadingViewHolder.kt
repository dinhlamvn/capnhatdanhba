package com.adomino.ddsdb.common.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adomino.ddsdb.R
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.common.model.LoadingUiModel
import com.adomino.ddsdb.recyclerview.XViewHolder

class LoadingViewHolder(view: View) : XViewHolder<LoadingUiModel>(view) {

  private val tvLoading: TextView by view.bindView(R.id.tvLoading)

  companion object {
    fun create(parent: ViewGroup): LoadingViewHolder {
      return with(LayoutInflater.from(parent.context).inflate(
          R.layout.loading,
          parent,
          false
      )) {
        LoadingViewHolder(this)
      }
    }
  }

  override fun bind(model: LoadingUiModel) {
    if (model.text.isNotEmpty()) {
      tvLoading.text = model.text
    }
  }
}