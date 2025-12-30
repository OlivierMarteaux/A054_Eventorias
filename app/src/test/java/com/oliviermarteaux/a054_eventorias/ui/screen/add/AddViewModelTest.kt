package com.oliviermarteaux.a054_eventorias.ui.screen.add

import androidx.lifecycle.SavedStateHandle
import com.google.firebase.auth.FirebaseUser
import com.oliviermarteaux.shared.cameraX.CameraRepository
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.firestore.data.repository.PostRepository
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Address
import com.oliviermarteaux.shared.ui.UiState
import com.oliviermarteaux.shared.test.rule.MainDispatcherRule
import com.oliviermarteaux.shared.utils.Logger
import com.oliviermarteaux.shared.utils.NoOpLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.oliviermarteaux.localshared.fake.fakeFirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class AddViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val postRepository: PostRepository = mock()
    private val userRepository: UserRepository = mock()
    private val cameraRepository: CameraRepository = mock()
    private val logger: Logger = NoOpLogger


    private val isOnlineFlow = MutableStateFlow(true)
    private val userAuthStateFlow = MutableStateFlow<FirebaseUser?>(null)

    private lateinit var viewModel: AddViewModel

    @Before
    fun setup() {

        userAuthStateFlow.value = null
        isOnlineFlow.value = true
        // 🔴 REQUIRED: AuthUserViewModel collects this in init {}
        whenever(userRepository.userAuthState).thenReturn(userAuthStateFlow)

        viewModel = AddViewModel(
            savedStateHandle = SavedStateHandle(),
            postRepository = postRepository,
            userRepository = userRepository,
            cameraRepository = cameraRepository,
            isOnlineFlow = isOnlineFlow,
            log = logger
        )
    }

    // ---------------------------------------------------------
    // Update post fields
    // ---------------------------------------------------------

    @Test
    fun updatePostTitle_whenCalled_shouldUpdatePostTitle() {
        // Given
        val title = "Compose Accessibility"

        // When
        viewModel.updatePostTitle(title)

        // Then
        assertEquals(title, viewModel.post.title)
    }

    @Test
    fun updatePostDescription_whenCalled_shouldUpdatePostDescription() {
        // Given
        val description = "Event description"

        // When
        viewModel.updatePostDescription(description)

        // Then
        assertEquals(description, viewModel.post.description)
    }

    @Test
    fun updatePostAddress_whenCalled_shouldWrapAddressCorrectly() {
        // Given
        val address = "Paris"

        // When
        viewModel.updatePostAddress(address)

        // Then
        assertEquals(Address(address), viewModel.post.address)
    }

    @Test
    fun updatePostPhoto_whenCalled_shouldUpdatePostPhotoUrl() {
        // Given
        val photoUrl = "photo.jpg"

        // When
        viewModel.updatePostPhoto(photoUrl)

        // Then
        assertEquals(photoUrl, viewModel.post.photoUrl)
    }

    // ---------------------------------------------------------
    // addPost
    // ---------------------------------------------------------

    @Test
    fun addPost_whenOffline_shouldNotAddPostAndResetUiState() = runTest {
        // Given
        isOnlineFlow.value = false

        // ⏱️ Let observeOnlineState() run
        advanceTimeBy(300)   // >= delay(200)
        advanceUntilIdle()

        var callbackCalled = false

        // When
        viewModel.addPost { callbackCalled = true }
        advanceUntilIdle()

        // Then
        verify(postRepository, never()).addPost(any())
        assertFalse(callbackCalled)
        assertTrue(viewModel.addPostUiState is UiState.Idle)
    }

    @Test
    fun addPost_whenRepositorySucceeds_shouldInvokeCallback() = runTest {
        // Given
        val user = fakeFirebaseUser()
        userAuthStateFlow.value = user

        whenever(postRepository.addPost(any()))
            .thenReturn(Result.success(Unit))

        var callbackCalled = false

        // When
        viewModel.addPost { callbackCalled = true }
        advanceUntilIdle()

        // Then
        verify(postRepository).addPost(any())
        assertTrue(callbackCalled)
        assertTrue(viewModel.addPostUiState is UiState.Idle)
    }

    @Test
    fun addPost_whenRepositoryFails_shouldShowErrorAndResetUiState() = runTest {
        // Given
        val user = fakeFirebaseUser()
        userAuthStateFlow.value = user

        whenever(postRepository.addPost(any()))
            .thenReturn(Result.failure(RuntimeException("Error")))

        // When
        viewModel.addPost {}
        advanceUntilIdle()

        // Then
        verify(postRepository).addPost(any())
        assertEquals(UiState.Idle, viewModel.addPostUiState)
    }
}