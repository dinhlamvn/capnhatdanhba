package com.adomino.ddsdb.ui.listcontact.uimodel

import android.view.View
import com.adomino.ddsdb.data.ContactUpdateInfo
import com.adomino.ddsdb.recyclerview.XModel

data class ContactUiModel(
  val id: String,
  val contactUpdateInfo: ContactUpdateInfo,
  val actionCall: View.OnClickListener,
  val actionItemClick: View.OnClickListener
) : XModel(id)