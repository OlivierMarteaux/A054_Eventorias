package com.oliviermarteaux.a054_eventorias

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.oliviermarteaux.shared.firebase.firebaseCloudMessaging.subscribeToFcmNotificationTopic
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

        try {
//            //_ initialize firebase
//            FirebaseApp.initializeApp(this)
//            Log.d("OM_TAG", "EventoriasApplication: onCreate(): FirebaseApp initialized")

            //_ Firebase authentification: sign out user at app start
            FirebaseAuth.getInstance().signOut()
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            Log.d("OM_TAG", "EventoriasApplication: onCreate(): FirebaseAuth signed out")
            Log.i("OM_TAG", "EventoriasApplication: onCreate(): firebaseUser = $firebaseUser")

//            //_ Firebase cloud messaging: create notif channel and subscribe topic
//            //_ not needed if only one default channel as it is created by MyFirebaseMessaging class
////            createDeviceNotificationChannel(
////                notifManager = getSystemService(NotificationManager::class.java)
////            )
//            //_ Firebase cloud messaging: subscribe to Firebase topic (Mandatory to receive notifs)
//            subscribeToFcmNotificationTopic()

            // manage application exceptions
        } catch (e: Exception) {
            Log.e("OM_TAG", "EventoriasApplication: onCreate(): FirebaseApp initialization failed", e)
        }
    }
}