package com.oliviermarteaux.a054_eventorias.ui.screen.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.oliviermarteaux.localshared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.localshared.firebase.authentication.domain.model.User
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.AuthUserViewModel
import com.oliviermarteaux.shared.utils.CoroutineDispatcherProvider
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val log: Logger,
    private val isOnlineFlow: Flow<Boolean>,
    private val dispatchers: CoroutineDispatcherProvider,
) : AuthUserViewModel(
    userRepository = userRepository,
    isOnlineFlow = isOnlineFlow,
    log = log,
) {
    var user: User by mutableStateOf(User())
        private set

    init {
        viewModelScope.launch {
            snapshotFlow { currentUser }
                .collect { currentUser ->
                    if (currentUser != null) {
                        user = currentUser
                        log.d("AccountViewModel: user updated to ${currentUser.email}")
                    }
                }
        }
    }
}