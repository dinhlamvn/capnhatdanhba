package com.adomino.ddsdb.data

data class PercentLoadingInfo(
  val message: String = "Loading...",
  val isShow: Boolean = false,
  val percent: Int = 0,
  val total: Int = 100
)