package com.adomino.ddsdb.ui.listcontact.uimodel

import com.adomino.ddsdb.recyclerview.XModel

data class ContactUpdateFailUiModel(
  val message: String,
  val onReload: () -> Unit
) : XModel("contactUpdateFail")