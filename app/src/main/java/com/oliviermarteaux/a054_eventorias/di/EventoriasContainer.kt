package com.oliviermarteaux.a054_eventorias.di

import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.firestore.data.repository.PostRepository

interface EventoriasContainer {
    val userRepository: UserRepository
    val postRepository: PostRepository
}