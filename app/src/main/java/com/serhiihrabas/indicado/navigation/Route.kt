package com.serhiihrabas.indicado.navigation

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object HomeScreen : Route()

    @Serializable
    data object TernopilOblEnergyScreen : Route()

}