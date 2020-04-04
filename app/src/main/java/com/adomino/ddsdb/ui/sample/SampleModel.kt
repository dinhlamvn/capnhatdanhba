package com.adomino.ddsdb.ui.sample

import com.adomino.ddsdb.recyclerview.XModel

data class SampleModel(
  val id: String,
  val name: String
) : XModel("sample")