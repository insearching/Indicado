package com.serhiihrabas.indicado.domain

import java.time.ZonedDateTime

data class PowerIndicator(
    val personalNumber: String,
    val lastName: String,
    val indicatorValue: String,
    val date: ZonedDateTime
)
