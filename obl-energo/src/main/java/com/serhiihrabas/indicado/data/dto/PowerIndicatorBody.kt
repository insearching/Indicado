package com.serhiihrabas.indicado.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EnergyReadingBody(
    val accountNumber: String,
    val meterNumber: String,
    val energyKindCode: String,
    val date: String,
    val originDetailId: Int,
    val value: String,
    val note: String
)