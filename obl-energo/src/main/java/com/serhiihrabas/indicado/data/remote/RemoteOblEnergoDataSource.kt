package com.serhiihrabas.indicado.data.remote

import com.serhiihrabas.core.data.network.NetworkResult
import com.serhiihrabas.core.data.network.safeCall
import com.serhiihrabas.indicado.data.dto.EnergyReadingBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val BASE_URL = "https://api.toe.com.ua"

class RemoteOblEnergoDataSource(
    private val client: HttpClient,
) {
    suspend fun sendPowerIndicator(body: EnergyReadingBody): NetworkResult<Unit> {
        return safeCall<Unit> {
            client.post("${BASE_URL}/api/content/pokaz/addPokaz/") {
                contentType(ContentType.Application.Json)
                setBody(body)
            }
        }
    }
}