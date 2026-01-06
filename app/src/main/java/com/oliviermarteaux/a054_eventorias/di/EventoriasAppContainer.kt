package com.oliviermarteaux.a054_eventorias.di

import android.content.Context
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserFirebaseRepository
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.authentication.data.service.UserApi
import com.oliviermarteaux.shared.firebase.authentication.data.service.UserFirebaseApi
import javax.inject.Singleton

class EventoriasAppContainer(context: Context)
    : EventoriasContainer {
    private val userApi: UserApi = UserFirebaseApi(context)

    override val userRepository: UserRepository by lazy {
        UserFirebaseRepository(userApi)
    }
}