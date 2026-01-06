package com.oliviermarteaux.a054_eventorias.di

import android.content.Context
import com.oliviermarteaux.a054_eventorias.fake.PostFakeRepository
import com.oliviermarteaux.a054_eventorias.fake.UserFakeRepository
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.firestore.data.repository.PostRepository

class EventoriasTestContainer(context: Context) : EventoriasContainer {

    override val userRepository: UserRepository =
        UserFakeRepository(context)

    override val postRepository: PostRepository =
        PostFakeRepository()
}