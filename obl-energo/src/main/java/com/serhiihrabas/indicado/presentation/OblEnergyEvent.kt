package com.serhiihrabas.indicado.presentation

sealed interface OblEnergyEvent {
    data class ShowMessage(val message: String): OblEnergyEvent
}