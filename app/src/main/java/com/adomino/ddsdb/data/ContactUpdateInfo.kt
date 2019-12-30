package ddsdb.vn.lcd.data

data class ContactUpdateInfo(
  val contactInfo: ContactInfo,
  val newPhoneNumber: String = ""
)