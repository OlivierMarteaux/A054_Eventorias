package com.oliviermarteaux.localshared.fake

import com.google.firebase.auth.FirebaseUser
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

internal fun fakeFirebaseUser(): FirebaseUser = mock {
    on { uid } doReturn "fake_uid"
    on { email } doReturn "user@test.com"
}