package com.oliviermarteaux.a054_eventorias

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The application class for the Eventorias application.
 * This class serves as the entry point for the application and can be used for global application-level
 * initialization tasks such as dependency injection setup using Hilt.
 */
@HiltAndroidApp
class EventoriasApplication : Application() {

    /**
     * Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
     */
    override fun onCreate() {
        super.onCreate()
    }
}