package com.adomino.ddsdb.extensions

const val PHONE_NUMBER_REGEX = "^(0|(\\+84))(%s)([0-9]{7})\$"

private val viettleNumbers = mapOf(
    "162" to "032",
    "163" to "033",
    "164" to "034",
    "165" to "035",
    "166" to "036",
    "167" to "037",
    "168" to "038",
    "169" to "039"
)

private val mobiphoneNumbers = mapOf(
    "120" to "070",
    "121" to "079",
    "122" to "077",
    "126" to "076",
    "128" to "078"
)

private val vinaphoneNumbers = mapOf(
    "123" to "083",
    "124" to "084",
    "125" to "085",
    "127" to "081",
    "129" to "082"
)

private val vietnamobileNumbers = mapOf(
    "186" to "056",
    "188" to "058"
)

private val gmobileNumbers = mapOf(
    "199" to "059"
)

fun String.mapToNewPhoneNumber(): String {
  listOf(
      viettleNumbers,
      mobiphoneNumbers,
      vinaphoneNumbers,
      vietnamobileNumbers,
      gmobileNumbers
  ).forEach { arrayNumber ->
    arrayNumber.forEach inner@{ (old, new) ->
      val newPhoneNumber = mapToNew(this.hasNotAnySpace(), old, new)
      if (newPhoneNumber.isNotEmpty()) return newPhoneNumber else return@inner
    }
  }

  return ""
}

fun String.isValidHeadNumber() = mapToNewPhoneNumber().isEmpty()

fun String.isInvalidHeadNumber() = mapToNewPhoneNumber().isNotEmpty()

fun String.hasNotAnySpace() = this.replace(" ", "", ignoreCase = true)

private fun mapToNew(
  phoneNumber: String,
  oldNumber: String,
  newNumber: String
): String {
  val regex = String.format(PHONE_NUMBER_REGEX, oldNumber)
  if (phoneNumber.matches(Regex(regex))) {
    return "$newNumber${phoneNumber.substring(phoneNumber.length - 7)}"
  }
  return ""
}

infix fun String.phoneNumberEqualsTo(other: String): Boolean {
  val p1 = startWithZero(this.hasNotAnySpace())
  val p2 = startWithZero(other.hasNotAnySpace())
  return p1 == p2
}

private fun startWithZero(phoneNumber: String): String {
  val newPhoneNumber = phoneNumber.map { c -> if (c < '0' || c > '9') "" else c.toString() }
      .filter { s -> s.isNotEmpty() }
      .joinToString(prefix = "", separator = "", postfix = "")
  if (newPhoneNumber.startsWith("84")) {
    return "0" + newPhoneNumber.substring(2)
  }
  return newPhoneNumber
}