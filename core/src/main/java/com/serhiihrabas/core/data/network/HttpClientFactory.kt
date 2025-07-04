package com.serhiihrabas.core.data.network

import com.serhiihrabas.core.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import kotlin.text.startsWith

object HttpClientFactory {

    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            headers {  }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        if (BuildConfig.DEBUG) {
                            Timber.tag("Ktor").i(message)
                        } else {
                            if (message.startsWith("REQUEST:") || message.startsWith("METHOD:")) {
                                Timber.tag("Ktor").i(message)
                            }
                        }
                    }
                }

                level = if (BuildConfig.DEBUG) {
                    LogLevel.ALL
                } else {
                    LogLevel.INFO // minimal, just request basics
                }
            }

            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}