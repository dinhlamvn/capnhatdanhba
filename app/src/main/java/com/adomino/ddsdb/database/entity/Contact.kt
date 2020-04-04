package com.adomino.ddsdb.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
  @PrimaryKey val uid: Int = 0,
  @ColumnInfo(name = "name") val name: String?,
  @ColumnInfo(name = "phone_number") val phoneNumber: String?
)