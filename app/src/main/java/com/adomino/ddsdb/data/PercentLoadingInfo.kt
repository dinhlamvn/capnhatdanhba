package ddsdb.vn.lcd.data

data class PercentLoadingInfo(
  val message: String = "Loading...",
  val isShow: Boolean = false,
  val percent: Int = 0,
  val total: Int = 100
)