package com.devileya.ulos

import android.app.Application
import com.devileya.ulos.domain.module.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Arif Fadly Siregar 11/10/20.
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }
}