package com.ot.playground.techtest

import android.app.Application
import com.ot.playground.techtest.di.playground_app
import com.ot.playground.techtest.di.remoteDatasourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PlaygroundApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)

            androidContext(this@PlaygroundApplication)
            androidFileProperties()
            modules(playground_app + remoteDatasourceModule)
        }
    }
}