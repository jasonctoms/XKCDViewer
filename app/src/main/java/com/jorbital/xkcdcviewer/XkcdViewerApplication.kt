package com.jorbital.xkcdcviewer

import android.app.Application
import com.jorbital.service.koin.XkcdKoinModule
import com.jorbital.xkcdcviewer.koin.BrowseKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

@Suppress("EXPERIMENTAL_API_USAGE")
class XkcdViewerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@XkcdViewerApplication)
            modules(listOf(XkcdKoinModule.module, BrowseKoinModule.module))
        }
    }
}