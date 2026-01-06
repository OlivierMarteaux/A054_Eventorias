package com.oliviermarteaux.a054_eventorias.di

import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository

interface EventoriasContainer {
    val userRepository: UserRepository
}