package com.oliviermarteaux.a054_eventorias.ui.screen.home

import androidx.compose.ui.text.input.TextFieldValue
import com.google.firebase.auth.FirebaseUser
import com.oliviermarteaux.shared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.shared.firebase.firestore.data.repository.PostRepository
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.shared.test.rule.MainDispatcherRule
import com.oliviermarteaux.shared.ui.ListUiState
import com.oliviermarteaux.shared.utils.Logger
import com.oliviermarteaux.shared.utils.NoOpLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val postRepository: PostRepository = mock()
    private val userRepository: UserRepository = mock()
    private val logger: Logger = NoOpLogger
    private val isOnlineFlow = MutableStateFlow(true)
    private lateinit var homeViewModel: HomeViewModel
    private val postsFlow = MutableSharedFlow<Result<List<Post>>>()
    private val userAuthStateFlow = MutableStateFlow<FirebaseUser?>(null)

    @Before
    fun setup() {
        whenever(userRepository.userAuthState).thenReturn(userAuthStateFlow)
        whenever(postRepository.posts).thenReturn(postsFlow)

        homeViewModel = HomeViewModel(
            postRepository = postRepository,
            userRepository = userRepository,
            log = logger,
            isOnlineFlow = isOnlineFlow
        )
    }

    // ---------------------------------------------------------
    // loadPosts
    // ---------------------------------------------------------

    @Test
    fun loadPosts_whenRepositoryEmitsSuccess_shouldExposeSuccessUiState() = runTest {
        // Given
        val posts = listOf(Post())
        postsFlow.emit(Result.success(posts))

        // When
        advanceUntilIdle()

        // Then
        assert(homeViewModel.homeUiState is ListUiState.Success)
        assertEquals(posts, homeViewModel.filteredPosts)
    }

    @Test
    fun loadPosts_whenRepositoryEmitsEmptyList_shouldExposeEmptyUiState() = runTest {
        // Given
        postsFlow.emit(Result.success(emptyList()))

        // When
        advanceUntilIdle()

        // Then
        assert(homeViewModel.homeUiState is ListUiState.Empty)
        assertTrue(homeViewModel.filteredPosts.isEmpty())
    }

    @Test
    fun loadPosts_whenRepositoryEmitsFailure_shouldExposeErrorUiState() = runTest {
        // Given
        val exception = IllegalStateException("Network error")
        postsFlow.emit(Result.failure(exception))

        // When
        advanceUntilIdle()

        // Then
        val state = homeViewModel.homeUiState
        assert(state is ListUiState.Error)
        assertEquals(exception, (state as ListUiState.Error).throwable)
    }

    // ---------------------------------------------------------
    // filterPosts
    // ---------------------------------------------------------

    @Test
    fun filterPosts_whenQueryMatchesTitle_shouldFilterCorrectly() = runTest {
        // Given
        val posts = listOf(
            Post(title = "Android"),
            Post(title = "iOS")
        )
        postsFlow.emit(Result.success(posts))
        advanceUntilIdle()

        // When
        homeViewModel.filterPosts(TextFieldValue("android"))

        // Then
        assertEquals(1, homeViewModel.filteredPosts.size)
        assertEquals("Android", homeViewModel.filteredPosts.first().title)
    }

    @Test
    fun clearQuery_whenCalled_shouldResetQueryAndRestorePosts() = runTest {
        // Given
        val posts = listOf(
            Post(title = "Compose"),
            Post(title = "XML")
        )
        postsFlow.emit(Result.success(posts))
        advanceUntilIdle()

        homeViewModel.filterPosts(TextFieldValue("Compose"))

        // When
        homeViewModel.clearQuery()

        // Then
        assertEquals("", homeViewModel.queryFieldValue.text)
        assertEquals(2, homeViewModel.filteredPosts.size)
    }

    // ---------------------------------------------------------
    // sortPostsBy
    // ---------------------------------------------------------

    @Test
    fun sortPostsBy_whenCalled_shouldSortFilteredPosts() = runTest {
        // Given
        val posts = listOf(
            Post(title = "B"),
            Post(title = "A")
        )
        postsFlow.emit(Result.success(posts))
        advanceUntilIdle()

        val sortOption = SortOption.TITLE

        // When
        homeViewModel.sortPostsBy(sortOption)

        // Then
        assertEquals("A", homeViewModel.filteredPosts.first().title)
        assertEquals(sortOption, homeViewModel.currentSortOption)
    }
}