package com.oliviermarteaux.a054_eventorias.ui.screen.account

import com.google.firebase.auth.FirebaseUser
import com.oliviermarteaux.localshared.fake.fakeFirebaseUser
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.ui.UiState
import com.oliviermarteaux.shared.datastore.NotificationPreferencesRepository
import com.oliviermarteaux.shared.test.rule.MainDispatcherRule
import com.oliviermarteaux.shared.utils.CoroutineDispatcherProvider
import com.oliviermarteaux.shared.utils.Logger
import com.oliviermarteaux.shared.utils.NoOpLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AccountViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userRepository: UserRepository = mock()
    private val notificationPreferencesRepository: NotificationPreferencesRepository = mock()
    private val logger: Logger = NoOpLogger
    private val dispatchers: CoroutineDispatcherProvider = mock()

    private val isOnlineFlow = MutableStateFlow(true)
    private val userAuthStateFlow = MutableStateFlow<FirebaseUser?>(null)
    private val notifStateFlow = MutableStateFlow(true)

    private lateinit var accountViewModel: AccountViewModel

    @Before
    fun setup() {
        // 🔴 REQUIRED — collected in AuthUserViewModel.init
        whenever(userRepository.userAuthState).thenReturn(userAuthStateFlow)

        // 🔴 REQUIRED — collected in AccountViewModel.init
        whenever(notificationPreferencesRepository.isNotifEnabled)
            .thenReturn(notifStateFlow)

        accountViewModel = AccountViewModel(
            userRepository = userRepository,
            notificationPreferencesRepository = notificationPreferencesRepository,
            log = logger,
            isOnlineFlow = isOnlineFlow,
            dispatchers = dispatchers
        )
    }

    // ---------------------------------------------------------
    // Init
    // ---------------------------------------------------------

    @Test
    fun accountViewModel_whenCreated_shouldExposeLoadingState() {
        // Given / When
        val vm = accountViewModel

        // Then
        assertTrue(vm.userUiState is UiState.Loading)
    }

    // ---------------------------------------------------------
    // toggleNotifications
    // ---------------------------------------------------------

    @Test
    fun toggleNotifications_whenCalled_shouldToggleAndPersistPreference() = runTest {
        // Given
        val initialState = accountViewModel.notificationState

        // When
        accountViewModel.toggleNotifications()
        advanceUntilIdle()

        // Then
        assertEquals(!initialState, accountViewModel.notificationState)
        verify(notificationPreferencesRepository)
            .saveNotificationPreference(!initialState)
    }

    // ---------------------------------------------------------
    // getNotifState
    // ---------------------------------------------------------

    @Test
    fun getNotifState_whenRepositoryEmits_shouldUpdateNotificationState() = runTest {
        // Given
        notifStateFlow.value = false

        // When
        advanceUntilIdle()

        // Then
        assertFalse(accountViewModel.notificationState)
    }

    // ---------------------------------------------------------
    // getCurrentUser
    // ---------------------------------------------------------

    @Test
    fun getCurrentUser_whenUserAvailable_shouldExposeSuccessUiState() = runTest {
        // Given
        val firebaseUser = fakeFirebaseUser()
        userAuthStateFlow.value = firebaseUser

        // When
        // ⏱️ Let AuthUserViewModel observers run
        advanceTimeBy(300) // >= delay(200)
        advanceUntilIdle()

        // Then
        assertTrue(accountViewModel.userUiState is UiState.Success)
        assertEquals(firebaseUser.email, accountViewModel.user.email)
    }

    @Test
    fun getCurrentUser_whenUserIsNull_shouldExposeErrorUiState() = runTest {
        // Given
        userAuthStateFlow.value = null

        // When
        advanceUntilIdle()

        // Then
        assertTrue(accountViewModel.userUiState is UiState.Error)
    }
}