package com.adomino.ddsdb.ui.listcontact

import com.adomino.ddsdb.recyclerview.XModel

data class ContactUiModel(
  val id: Int,
  val name: String
) : XModel {
  override fun isEquals(other: XModel?): Boolean {
    return this.id == (other as ContactUiModel).id
  }

  override fun isEqualsContents(other: XModel?): Boolean {
    val model = other as ContactUiModel
    return this.id == model.id && this.name == model.name
  }

  override fun viewType(): Int {
    return 0
  }
}