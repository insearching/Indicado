package com.serhiihrabas.indicado.domain

import com.serhiihrabas.core.data.network.NetworkResult

interface OblEnergoRepository {
    suspend fun sendPowerIndicator(powerIndicator: PowerIndicator): NetworkResult<Unit>
}