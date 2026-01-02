package com.oliviermarteaux.a054_eventorias.fake

import android.content.Context
import androidx.annotation.StringRes
import com.google.firebase.auth.FirebaseUser
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.extensions.toUriString
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.authentication.domain.model.NewUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing user data.
 *
 * @property userApi The API for interacting with user data.
 */
@Singleton
class UserFakeRepository @Inject constructor(
    private val context: Context
): UserRepository {

    val testUser = User(
        id = "1",
        firstname = "Fievel",
        lastname = "Farwest",
        fullname = "Fievel Farwest",
        email = "fievelfarwest@example.com",
        photoUrl = R.drawable.fievel_farwest.toUriString(context)
    )
    /**
     * A flow that emits the current authentication state of the user.
     * Emits a [FirebaseUser] if a user is signed in, or `null` otherwise.
     */
    override val userAuthState: Flow<User?> = flowOf(testUser)
    /**
     * Checks if an email address is already registered.
     *
     * @param email The email address to check.
     * @return A [Result] indicating whether the email exists. `Result.success(true)` if it exists, `Result.success(false)` otherwise.
     */
    override suspend fun checkEmail(email: String): Result<Boolean> = Result.success(true)
    /**
     * Creates a new user account.
     *
     * @param newUser The details of the new user.
     * @return A [Result] containing the created [User] on success, or an error.
     */
    override suspend fun createAccount(newUser: NewUser): Result<User?> = Result.success(testUser)
    /**
     * Signs in a user with their email and password.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @return A [Result] containing the signed-in [User] on success, or an error.
     */
    override suspend fun signIn(email: String, password: String): Result<User?> =
        Result.success(testUser)
    /**
     * Sends a password reset email to the specified email address.
     *
     * @param email The email address to send the reset link to.
     * @return A [Result] indicating success or failure.
     */
    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> =
        Result.success(Unit)
    /**
     * Signs out the current user.
     *
     * @return A [Result] containing the signed-out [User] on success, or an error.
     */
    override fun signOut(): Result<User?> = Result.success(testUser)
    /**
     * Deletes the current user's account.
     *
     * @return A [Result] containing the deleted [User] on success, or an error.
     */
    override suspend fun deleteAccount(): Result<User?> = Result.success(testUser)


    override suspend fun signInWithGoogle(@StringRes serverClientIdStringRes: Int): Result<User?> =
        Result.success(testUser)
}