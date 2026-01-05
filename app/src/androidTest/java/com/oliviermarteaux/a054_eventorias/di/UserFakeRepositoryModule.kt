package com.oliviermarteaux.a054_eventorias.di

import android.content.Context
import com.oliviermarteaux.a054_eventorias.fake.UserFakeRepository
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [UserModule::class]
//)
//object FakeUserRepositoryModule {
//    @Provides
//    @Singleton
//    fun provideUserRepository(@ApplicationContext context: Context): UserRepository =
//        UserFakeRepository(context)
//}