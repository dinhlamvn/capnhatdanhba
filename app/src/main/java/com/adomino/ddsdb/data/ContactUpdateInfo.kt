package com.adomino.ddsdb.data

data class ContactUpdateInfo(
  val contactInfo: ContactInfo,
  val newPhoneNumber: String = ""
)