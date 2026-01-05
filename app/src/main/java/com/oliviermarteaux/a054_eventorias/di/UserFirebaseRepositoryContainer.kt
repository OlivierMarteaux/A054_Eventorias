package com.oliviermarteaux.a054_eventorias.di

import android.content.Context
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserFirebaseRepository
import com.oliviermarteaux.shared.firebase.authentication.data.service.UserApi
import com.oliviermarteaux.shared.firebase.authentication.data.service.UserFirebaseApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserFirebaseRepositoryContainer(context: Context)
    : UserRepositoryContainer {
    private val userApi: UserApi = UserFirebaseApi(context)

    override val userRepository = UserFirebaseRepository(userApi)
}