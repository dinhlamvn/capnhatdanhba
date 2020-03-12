package com.adomino.ddsdb.ui.listcontact

import android.view.View
import android.widget.TextView
import com.adomino.ddsdb.R
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.recyclerview.XViewHolder

class ListContactViewHolder(view: View) : XViewHolder<ContactUiModel>(view) {

  private val tvDisplayName: TextView by view.bindView(R.id.tvDisplayName)
  private val tvPhoneNumber: TextView by view.bindView(R.id.tvPhoneNumber)
  private val tvNewPhoneNumber: TextView by view.bindView(R.id.tvNewPhoneNumber)

  override fun bind(model: ContactUiModel) {
    val contactInfo = model.contactUpdateInfo.contactInfo
    tvDisplayName.text = contactInfo.displayName
    tvPhoneNumber.text = contactInfo.phoneNumber
    tvNewPhoneNumber.text = model.contactUpdateInfo.newPhoneNumber
  }
}