package com.serhiihrabas.indicado.utils

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

val dateParseFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val dateDisplayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
val dateSendFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun String.parseToZonedDateTime(
    zoneId: ZoneId = ZoneId.systemDefault(),
    formatter: DateTimeFormatter = dateDisplayFormatter,
): ZonedDateTime {
    val localDate = LocalDate.parse(this, formatter)
    return localDate.atStartOfDay(zoneId)
}