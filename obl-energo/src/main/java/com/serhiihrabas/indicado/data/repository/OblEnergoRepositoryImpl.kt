package com.serhiihrabas.indicado.data.repository

import com.serhiihrabas.core.data.network.NetworkResult
import com.serhiihrabas.indicado.data.mapper.toDto
import com.serhiihrabas.indicado.data.remote.RemoteOblEnergoDataSource
import com.serhiihrabas.indicado.domain.OblEnergoRepository
import com.serhiihrabas.indicado.domain.PowerIndicator

class OblEnergoRepositoryImpl(
    private val remoteDataSource: RemoteOblEnergoDataSource
): OblEnergoRepository {
    override suspend fun sendPowerIndicator(powerIndicator: PowerIndicator): NetworkResult<Unit> {
        return remoteDataSource.sendPowerIndicator(powerIndicator.toDto())
    }
}