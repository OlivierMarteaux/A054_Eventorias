package com.oliviermarteaux.a054_eventorias.ui.screen.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.localshared.firebase.authentication.domain.model.User
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.AuthUserViewModel
import com.oliviermarteaux.localshared.firebase.messaging.SharedMessagingService
import com.oliviermarteaux.shared.datastore.NotificationPreferencesRepository
import com.oliviermarteaux.shared.utils.CoroutineDispatcherProvider
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val notificationPreferencesRepository: NotificationPreferencesRepository,
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
    var notificationState: Boolean by mutableStateOf(true)
        private set

    /**
     * Toggles the notification preference.
     *
     * @param isNotifEnabled Whether notifications should be enabled.
     */
    fun toggleNotifications() {
        viewModelScope.launch {
            notificationState = !notificationState
            notificationPreferencesRepository.saveNotificationPreference(notificationState)
            log.d("AccountViewModel: toggleNotifications(): $notificationState")
        }
    }

    /**
     * Gets the post from the repository.
     */
    private fun getNotifState(){
        viewModelScope.launch {
            notificationPreferencesRepository.isNotifEnabled.collect { result ->
                notificationState = result
                log.d("AccountViewModel: getNotifState(): notificationState = $notificationState")
            }
        }
    }

    /**
     * Gets the current user logged in Firebase.
     */
    private fun getCurrentUser(){
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

    init {
        getCurrentUser()
        getNotifState()
    }
}