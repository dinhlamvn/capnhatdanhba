package com.adomino.ddsdb.ui.listcontact

import com.adomino.ddsdb.recyclerview.XModel

class ContactEmptyResult() : XModel {

  override fun isEquals(other: XModel?): Boolean {
    return false
  }

  override fun isEqualsContents(other: XModel?): Boolean {
    return false
  }

  override fun viewType(): Int {
    return 1
  }
}