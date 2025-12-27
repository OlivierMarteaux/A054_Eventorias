package com.oliviermarteaux.localshared.firebase.authentication.data.repository

import com.oliviermarteaux.localshared.fake.FakeDataFactory.fakeNewUser
import com.oliviermarteaux.localshared.fake.FakeDataFactory.fakeUser
import com.oliviermarteaux.shared.firebase.authentication.data.service.UserApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserRepositoryTest {

    private lateinit var userApi: UserApi
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userApi = mockk()
        coEvery { userApi.userAuthState } returns mockk()
        userRepository = UserRepository(userApi)
    }

    @Test
    fun checkEmail_whenCalled_DelegatesToApi() = runTest {
        // Given
        coEvery { userApi.checkEmail(any()) } returns Result.success(true)
        // When
        userRepository.checkEmail(fakeUser.email)
        // Then
        coVerify { userApi.checkEmail(fakeUser.email) }
    }

    @Test
    fun createAccount_whenCalled_DelegatesToApi() = runTest {
        // Given
        coEvery { userApi.createAccount(any()) } returns Result.success(fakeUser)
        // When
        userRepository.createAccount(fakeNewUser)
        // Then
        coVerify { userApi.createAccount(fakeNewUser) }
    }

    @Test
    fun signIn_whenCalled_DelegatesToApi() = runTest {
        val email = "james.buchanan@examplepetstore.com"
        val password = "password"
        // Given
        coEvery { userApi.signIn(any(), any()) } returns Result.success(fakeUser)
        // When
        userRepository.signIn(email, password)
        // Then
        coVerify { userApi.signIn(email, password) }
    }

    @Test
    fun sendPasswordResetEmail_whenCalled_DelegatesToApi() = runTest {
        // Given
        coEvery { userApi.sendPasswordResetEmail(any()) } returns Result.success(Unit)
        // When
        userRepository.sendPasswordResetEmail(fakeUser.email)
        // Then
        coVerify { userApi.sendPasswordResetEmail(fakeUser.email) }
    }

    @Test
    fun signOut_whenCalled_DelegatesToApi() = runTest {
        // Given
        coEvery { userApi.signOut() } returns Result.success(fakeUser)
        // When
        userRepository.signOut()
        // Then
        coVerify { userApi.signOut() }
    }

    @Test
    fun deleteAccount_whenCalled_DelegatesToApi() = runTest {
        // Given
        coEvery { userApi.deleteAccount() } returns Result.success(fakeUser)
        // When
        userRepository.deleteAccount()
        // Then
        coVerify { userApi.deleteAccount() }
    }

    @Test
    fun signInWithGoogle_whenCalled_DelegatesToApi() = runTest {
        // Given
        coEvery { userApi.signInWithGoogle() } returns Result.success(fakeUser)
        // When
        userRepository.signInWithGoogle()
        // Then
        coVerify { userApi.signInWithGoogle() }
    }
}