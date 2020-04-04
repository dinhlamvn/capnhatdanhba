package com.adomino.ddsdb.common.model

import com.adomino.ddsdb.recyclerview.XModel

data class LoadingUiModel(
  val id: String,
  val text: String = ""
) : XModel(id)