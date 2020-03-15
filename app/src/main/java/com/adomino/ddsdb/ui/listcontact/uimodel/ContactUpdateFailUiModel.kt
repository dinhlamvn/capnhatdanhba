package com.adomino.ddsdb.ui.listcontact.uimodel

import com.adomino.ddsdb.recyclerview.XModel

data class ContactUpdateFailUiModel(
  val message: String,
  val onReload: () -> Unit
) : XModel("contactUpdateFail") {

  companion object {
    const val VIEW_TYPE = 3
  }

  override fun viewType(): Int {
    return VIEW_TYPE
  }

  override fun hashId(): Int {
    return 1001
  }
}