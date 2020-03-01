package com.adomino.ddsdb.ui.sample

import com.adomino.ddsdb.recyclerview.XModel

data class SampleModel(
  val id: String,
  val name: String
) : XModel {
  override fun isEquals(other: XModel?): Boolean {
    return this.id == (other as SampleModel).id
  }

  override fun isEqualsContents(other: XModel?): Boolean {
    val otherModel = other as SampleModel
    return this.id == otherModel.id && this.name == otherModel.name
  }

  override fun viewType(): Int {
    return 0
  }
}