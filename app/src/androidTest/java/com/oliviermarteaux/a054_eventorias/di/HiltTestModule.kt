package com.oliviermarteaux.a054_eventorias.di

import androidx.test.core.app.ApplicationProvider
import com.oliviermarteaux.a054_eventorias.fake.UserFakeRepository
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],  // Same component as production
    replaces = [AppModule::class]             // Your real module that provides UserApi
)
class HiltTestModule {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserFakeRepository(
            context = ApplicationProvider.getApplicationContext()
        )
    }
}