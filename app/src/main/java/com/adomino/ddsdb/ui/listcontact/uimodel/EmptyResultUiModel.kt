package com.adomino.ddsdb.ui.listcontact.uimodel

import com.adomino.ddsdb.recyclerview.XModel

class EmptyResultUiModel(id: Int) : XModel(id) {

  override fun viewType(): Int {
    return 1
  }

  override fun hashId(): Int {
    return hashCode()
  }
}