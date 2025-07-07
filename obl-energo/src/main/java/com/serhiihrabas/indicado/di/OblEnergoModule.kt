package com.serhiihrabas.indicado.di

import com.serhiihrabas.indicado.data.remote.RemoteOblEnergoDataSource
import com.serhiihrabas.indicado.data.repository.OblEnergoRepositoryImpl
import com.serhiihrabas.indicado.domain.OblEnergoRepository
import com.serhiihrabas.indicado.presentation.OblEnergyViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val oblEnergoModule = module {
    viewModelOf(::OblEnergyViewModel)
    singleOf(::RemoteOblEnergoDataSource)
    singleOf(::OblEnergoRepositoryImpl) { bind<OblEnergoRepository>() }
}