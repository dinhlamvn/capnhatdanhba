package com.adomino.ddsdb.ui.listcontact.uimodel

import com.adomino.ddsdb.data.ContactUpdateInfo
import com.adomino.ddsdb.recyclerview.XModel

data class ContactUiModel(
  val id: String,
  val contactUpdateInfo: ContactUpdateInfo
) : XModel(id) {

  companion object {
    const val VIEW_TYPE = 0
  }

  override fun viewType(): Int {
    return VIEW_TYPE
  }

  override fun hashId(): Int {
    return hashCode()
  }
}