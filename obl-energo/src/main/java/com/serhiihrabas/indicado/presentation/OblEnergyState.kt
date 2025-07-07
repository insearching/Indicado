package com.serhiihrabas.indicado.presentation

import java.time.ZonedDateTime

data class OblEnergyState(
    val accountNumber: String = "",
    val lastName: String = "",
    val indicatorValue: String = "",
    val date: ZonedDateTime = ZonedDateTime.now(),
)