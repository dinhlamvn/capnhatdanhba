package com.adomino.ddsdb.ui.sample

import com.adomino.ddsdb.recyclerview.XModel

data class SampleModel(
  val id: String,
  val name: String
) : XModel(0) {

  override fun viewType(): Int {
    return -1
  }

  override fun hashId(): Int {
    return -1
  }
}