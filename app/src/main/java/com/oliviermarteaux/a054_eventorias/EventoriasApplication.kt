package com.oliviermarteaux.a054_eventorias

import android.app.Application
import android.content.Context
import android.util.Log
import coil3.ImageLoader
import coil3.SingletonImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.oliviermarteaux.a054_eventorias.di.UserFirebaseRepositoryContainer
import com.oliviermarteaux.a054_eventorias.di.UserRepositoryContainer
import com.oliviermarteaux.shared.di.AppContainer
import com.oliviermarteaux.shared.firebase.messaging.subscribeToFcmNotificationTopic
import dagger.hilt.android.HiltAndroidApp

/**
 * The application class for the Eventorias application.
 * This class serves as the entry point for the application and can be used for global application-level
 * initialization tasks such as dependency injection setup using Hilt.
 */
@HiltAndroidApp
class EventoriasApplication : Application(), SingletonImageLoader.Factory {

    lateinit var userRepositoryContainer: UserRepositoryContainer

    /**
     * Creates a new [ImageLoader] for the application.
     *
     * @param context The application context.
     * @return A new [ImageLoader] instance.
     */
    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context = context)
            .build()
    }

    /**
     * Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
     */
    override fun onCreate() {
        super.onCreate()

        userRepositoryContainer = UserFirebaseRepositoryContainer(this)

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
            subscribeToFcmNotificationTopic()

            // manage application exceptions
        } catch (e: Exception) {
            Log.e("OM_TAG", "EventoriasApplication: onCreate(): FirebaseApp initialization failed", e)
        }
    }
}