package com.openclassrooms.hexagonal.games.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import com.oliviermarteaux.localshared.firebase.authentication.data.service.UserApi
import com.oliviermarteaux.localshared.firebase.authentication.data.service.UserFirebaseApi
import com.oliviermarteaux.localshared.firebase.firestore.data.repository.PostFirebaseRepository
import com.oliviermarteaux.localshared.firebase.firestore.data.repository.PostRepository
import com.oliviermarteaux.localshared.firebase.firestore.data.service.PostApi
import com.oliviermarteaux.localshared.firebase.firestore.data.service.PostFirebaseApi
import com.oliviermarteaux.shared.utils.AndroidLogger
import com.oliviermarteaux.shared.utils.CoroutineDispatcherProvider
import com.oliviermarteaux.shared.utils.Logger
import com.oliviermarteaux.shared.utils.checkInternetConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

/**
 * This class acts as a Dagger Hilt module, responsible for providing dependencies to other parts of the application.
 * It's installed in the SingletonComponent, ensuring that dependencies provided by this module are created only once
 * and remain available throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
  /**
   * Provides a Singleton instance of PostApi using a PostFirebaseApi implementation.
   * This means that whenever a dependency on PostApi is requested, the same instance of PostFirebaseApi will be used
   * throughout the application.
   *
   * @return A Singleton instance of PostFirebaseApi.
   */
  @Provides
  @Singleton
  fun providePostApi(): PostApi {
    return PostFirebaseApi()
  }

  /**
   * Provides a singleton instance of [UserApi].
   *
   * @return A singleton instance of [UserFirebaseApi].
   */
  @Singleton
  @Provides
  fun provideUserApi(
      @ApplicationContext context: Context
  ): UserApi {
    return UserFirebaseApi(context)
  }

    @Provides
    @Singleton
    fun providePostRepository(
        postApi: PostApi,
    ): PostRepository {
        return PostFirebaseRepository(postApi)
    }

  /**
   * Provides the [NotificationManager] system service.
   *
   * @param app The application instance.
   * @return The [NotificationManager] instance.
   */
  @Provides
  fun provideNotificationManager(app: Application): NotificationManager =
    app.getSystemService(NotificationManager::class.java)

//  /**
//   * Provides a singleton instance of [DataStore] for [Preferences].
//   *
//   * @param context The application context.
//   * @return A singleton instance of [DataStore] for user preferences.
//   */
//  @Provides
//  @Singleton
//  fun providePreferencesDataStore(
//    @ApplicationContext context: Context
//  ): DataStore<Preferences> =
//    PreferenceDataStoreFactory.create {
//      context.preferencesDataStoreFile("user_preferences")
//    }

  /**
   * Provides a singleton instance of [Logger].
   *
   * @return A singleton instance of [AndroidLogger].
   */
  @Provides
  @Singleton
  fun provideLogger(): Logger = AndroidLogger

  /**
   * Provides a [Flow] of [Boolean] that indicates the internet connection status.
   *
   * @param context The application context.
   * @return A [Flow] that emits `true` if the device is online, `false` otherwise.
   */
  @Provides
  fun provideIsOnlineFlow(
    @ApplicationContext context: Context
  ): Flow<Boolean> = checkInternetConnection(context)

  /**
   * Provides a singleton instance of [CoroutineDispatcherProvider].
   *
   * @return A singleton instance of [CoroutineDispatcherProvider].
   */
  @Provides
  @Singleton
  fun provideCoroutineDispatcherProvider(): CoroutineDispatcherProvider {
    return CoroutineDispatcherProvider()
  }
}
