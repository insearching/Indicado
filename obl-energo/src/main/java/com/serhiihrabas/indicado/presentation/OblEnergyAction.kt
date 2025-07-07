package com.serhiihrabas.indicado.presentation

import java.time.ZonedDateTime

sealed interface OblEnergyAction {
    data class OnAccountNumberChange(val accountNumber: String) : OblEnergyAction

    data class OnLastNameChange(val lastName: String) : OblEnergyAction

    data class OnIndicatorValueChange(val indicatorValue: String) : OblEnergyAction

    data class OnDateChange(val date: ZonedDateTime) : OblEnergyAction

    data object OnSendIndicator : OblEnergyAction
}