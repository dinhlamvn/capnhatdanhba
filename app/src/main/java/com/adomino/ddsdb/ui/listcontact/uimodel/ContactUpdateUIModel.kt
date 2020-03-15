package com.adomino.ddsdb.ui.listcontact.uimodel

import com.adomino.ddsdb.recyclerview.XModel

data class ContactUpdateUIModel(
  val message: String
) : XModel("contactUpdateLoading") {

  companion object {
    const val VIEW_TYPE = 2
  }

  override fun viewType(): Int {
    return VIEW_TYPE
  }

  override fun hashId(): Int {
    return 1000
  }
}