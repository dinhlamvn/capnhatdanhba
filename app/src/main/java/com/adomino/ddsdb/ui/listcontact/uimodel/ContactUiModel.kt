package com.adomino.ddsdb.ui.listcontact.uimodel

import com.adomino.ddsdb.data.ContactUpdateInfo
import com.adomino.ddsdb.recyclerview.XModel

data class ContactUiModel(
  val id: Int,
  val contactUpdateInfo: ContactUpdateInfo
) : XModel(id) {

  override fun viewType(): Int {
    return 0
  }

  override fun hashId(): Int {
    return hashCode()
  }
}