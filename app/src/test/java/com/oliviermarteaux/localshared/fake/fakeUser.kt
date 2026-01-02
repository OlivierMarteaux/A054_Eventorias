package com.oliviermarteaux.localshared.fake

import com.google.firebase.auth.FirebaseUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

val fakeUser = User(
    id = "fake_uid",
    email = "user@test.com"
)