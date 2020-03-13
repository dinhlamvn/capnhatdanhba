package com.adomino.ddsdb.common.model

import com.adomino.ddsdb.recyclerview.XModel

data class LoadingUiModel(
  val text: String = ""
) : XModel() {

  companion object {
    const val ID: Int = 9999
  }

  override fun viewType(): Int {
    return ID
  }

  override fun hashId(): Int {
    return ID
  }
}