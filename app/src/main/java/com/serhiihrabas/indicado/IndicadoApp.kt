package com.serhiihrabas.indicado

import android.app.Application
import com.serhiihrabas.core.di.coreModule
import com.serhiihrabas.indicado.di.oblEnergoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class IndicadoApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@IndicadoApp)
            modules(coreModule, oblEnergoModule)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}