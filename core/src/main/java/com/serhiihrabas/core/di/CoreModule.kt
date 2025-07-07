package com.serhiihrabas.core.di

import com.serhiihrabas.core.data.network.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val coreModule = module {
    single { HttpClientFactory.create(CIO.create()) }
}