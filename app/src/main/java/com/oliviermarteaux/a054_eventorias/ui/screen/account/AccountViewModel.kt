package com.oliviermarteaux.a054_eventorias.ui.screen.account

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.oliviermarteaux.a054_eventorias.EventoriasApplication
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.authentication.domain.mapper.toUser
import com.oliviermarteaux.shared.firebase.authentication.domain.model.User
import com.oliviermarteaux.shared.firebase.authentication.ui.screen.AuthUserViewModel
import com.oliviermarteaux.shared.datastore.NotificationPreferencesRepository
import com.oliviermarteaux.shared.ui.UiState
import com.oliviermarteaux.shared.utils.CoroutineDispatcherProvider
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okhttp3.internal.platform.PlatformRegistry.applicationContext
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
    val containerUserRepository: UserRepository by mutableStateOf ((applicationContext as EventoriasApplication).userRepositoryContainer.userRepository)

    var user: User by mutableStateOf(User())
        private set
    var notificationState: Boolean by mutableStateOf(true)
        private set

    var userUiState: UiState<User> by mutableStateOf(UiState.Loading)

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
//            delay(1500) // for test
            /* fixed: snapshotFlow only emits when a Compose snapshot is applied.
                then it cannot be tested thru Unit Test.
                ==> Use Flow.map.collect instead.
             */
//            snapshotFlow { currentUser }
//                .collect { currentUser ->
//                    try {
//                        userUiState = UiState.Success(currentUser!!)
//                        user = currentUser
//                        log.d("AccountViewModel: user updated to ${currentUser.email}")
//                    } catch (e: Exception){
//                        userUiState = UiState.Error(e)
//                    }
//                }
            containerUserRepository.userAuthState
//                .map { it}
//                .filterNotNull()
                .collect { currentUser ->
                    if (currentUser != null) {
                        userUiState = UiState.Success(currentUser)
                        this@AccountViewModel.user = currentUser
                        log.d("AccountViewModel: user updated to ${currentUser.email}")
                        log.d("AccountViewModel: userPhotoUrl = ${currentUser.photoUrl}")
                    } else {
                        userUiState = UiState.Error(Throwable("No user logged in"))
                        log.d("AccountViewModel: no user logged in")
                        log.d("AccountViewModel: userPhotoUrl = ${user.photoUrl}")
                    }
                }
        }
    }

    init {
        userUiState = UiState.Loading
        getCurrentUser()
        getNotifState()
//        if (BuildConfig.DEBUG) {
//            val fakeUser = User(
//                id = "123",
//                firstname = "Fievel",
//                lastname = "Farwest",
//                fullname = "Fievel Farwest",
//                email = "fievelfarwest@example.com",
//            )
//            userUiState = UiState.Success(fakeUser)
//            user = fakeUser
//            notificationState = true
//        } else {
//            userUiState = UiState.Loading
//            getCurrentUser()
//            getNotifState()
//        }
    }
}