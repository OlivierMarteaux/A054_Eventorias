package com.oliviermarteaux.localshared.firebase.authentication.data.service

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.oliviermarteaux.a054_eventorias.R
import kotlinx.coroutines.tasks.await

class GoogleAuthFirebaseApi (
    private val context: Context
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)
    /**
     * Launch Google Sign-In using Credential Manager
     * Returns the signed-in FirebaseUser or null
     */
    suspend fun googleSignIn(): FirebaseUser? {
        // Build the Google ID option
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.default_web_client_id))
            .build()

        // Create the request
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return try {
            val result = credentialManager.getCredential(context, request)
            val credential = result.credential
            val idToken = (credential as? GoogleIdTokenCredential)
                ?.idToken ?: return null

            // Sign in to Firebase
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(firebaseCredential).await()
            authResult.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}