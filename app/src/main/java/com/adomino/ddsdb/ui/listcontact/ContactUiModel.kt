package com.adomino.ddsdb.ui.listcontact

import com.adomino.ddsdb.data.ContactInfo
import com.adomino.ddsdb.data.ContactUpdateInfo
import com.adomino.ddsdb.recyclerview.XModel

data class ContactUiModel(
  val contactUpdateInfo: ContactUpdateInfo = ContactUpdateInfo(
      contactInfo = ContactInfo()
  )
) : XModel {
  override fun isEquals(other: XModel?): Boolean {
    val o = other as ContactUiModel
    return this.contactUpdateInfo.contactInfo.id == o.contactUpdateInfo.contactInfo.id
  }

  override fun isEqualsContents(other: XModel?): Boolean {
    val o = other as ContactUiModel
    return this.contactUpdateInfo.contactInfo == o.contactUpdateInfo.contactInfo
  }

  override fun viewType(): Int {
    return 0
  }
}