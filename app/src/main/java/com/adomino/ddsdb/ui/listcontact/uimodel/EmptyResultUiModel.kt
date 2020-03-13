package com.adomino.ddsdb.ui.listcontact.uimodel

import com.adomino.ddsdb.recyclerview.XModel

class EmptyResultUiModel : XModel() {

  override fun viewType(): Int {
    return 1
  }

  override fun hashId(): Int {
    return hashCode()
  }
}