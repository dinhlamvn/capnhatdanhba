package com.adomino.ddsdb.ui.listcontact.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.adomino.ddsdb.R
import com.adomino.ddsdb.common.bindView
import com.adomino.ddsdb.recyclerview.XViewHolder
import com.adomino.ddsdb.ui.listcontact.uimodel.ContactUiModel

class ContactItemViewHolder(view: View) : XViewHolder<ContactUiModel>(view) {

  companion object {
    fun create(parent: ViewGroup): ContactItemViewHolder {
      return with(
          LayoutInflater.from(parent.context)
              .inflate(
                  R.layout.contact_item_view,
                  parent,
                  false
              )
      ) {
        ContactItemViewHolder(this)
      }
    }
  }

  private val tvDisplayName: TextView by view.bindView(R.id.tvDisplayName)
  private val tvPhoneNumber: TextView by view.bindView(R.id.tvPhoneNumber)
  private val tvNewPhoneNumber: TextView by view.bindView(R.id.tvNewPhoneNumber)
  private val tvArrow: TextView by view.bindView(R.id.tvArrow)
  private val ivCall: ImageView by view.bindView(R.id.ivCall)

  override fun bind(model: ContactUiModel) {
    Log.d("DinhLam", "Contact info ${model.contactUpdateInfo.contactInfo.displayName}")
    val contactInfo = model.contactUpdateInfo.contactInfo
    tvDisplayName.text = contactInfo.displayName
    tvPhoneNumber.text = contactInfo.phoneNumber

    if (model.contactUpdateInfo.newPhoneNumber.isEmpty()) {
      tvArrow.isVisible = false
      tvNewPhoneNumber.isVisible = false
    } else {
      tvArrow.isVisible = true
      tvNewPhoneNumber.isVisible = true
      tvNewPhoneNumber.text = model.contactUpdateInfo.newPhoneNumber
    }

    itemView.setOnClickListener(model.actionItemClick)

    ivCall.setOnClickListener(model.actionCall)
  }
}