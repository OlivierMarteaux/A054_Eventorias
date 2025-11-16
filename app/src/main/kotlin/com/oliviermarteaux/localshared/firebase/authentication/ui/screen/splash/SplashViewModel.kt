package com.oliviermarteaux.localshared.firebase.authentication.ui.screen.splash

import androidx.lifecycle.viewModelScope
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.AuthUserViewModel
import com.oliviermarteaux.localshared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.utils.CoroutineDispatcherProvider
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val log: Logger,
    private val isOnlineFlow: Flow<Boolean>,
) : AuthUserViewModel(
    userRepository = userRepository,
    isOnlineFlow = isOnlineFlow,
    log = log,
) {
    fun signInWithGoogle(onSuccess: () -> Unit) {
        viewModelScope.launch {
            userRepository.signInWithGoogle().fold(
                onSuccess = {
                    log.d("splashViewModel::signInWithGoogle: successfully logged")
                    onSuccess()
                            },
                onFailure = { e -> log.e("splashViewModel::signInWithGoogle: failed to log",e) }
            )
        }
    }
}