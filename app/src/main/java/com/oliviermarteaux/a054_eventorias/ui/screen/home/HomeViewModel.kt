package com.oliviermarteaux.a054_eventorias.ui.screen.home

import android.R.attr.name
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliviermarteaux.localshared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.AuthUserViewModel
import com.oliviermarteaux.localshared.firebase.firestore.data.repository.PostRepository
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.localshared.firebase.firestore.utils.uploadSamplePosts
import com.oliviermarteaux.shared.ui.ListUiState
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing data and events related to the Home screen.
 * This ViewModel retrieves posts from the PostRepository and exposes them as a Flow<List<Post>>,
 * allowing UI components to observe and react to changes in the posts data.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val log: Logger,
    private val isOnlineFlow: Flow<Boolean>
) : AuthUserViewModel(
    userRepository = userRepository,
    isOnlineFlow = isOnlineFlow,
    log = log,
) {
    /**
     * The UI state for the home feed.
     */
    var homeUiState: ListUiState<Post> by mutableStateOf(ListUiState.Loading)
        private set

    var query: String by mutableStateOf("")
        private set
    fun onQueryChange(newQuery: String) { query = newQuery }

    var searchBar: Boolean by mutableStateOf(false)
        private set
    fun showSearchBar() { searchBar = !searchBar }

    /**
     * Loads the posts from the repository.
     */
    fun loadPosts() {
        viewModelScope.launch {
            homeUiState = ListUiState.Loading
//      delay(3000) // simulate network delay for Loading state evidence
            postRepository.posts.collect { result ->
                result
                    .onSuccess { posts ->
                        homeUiState =
                            if (posts.isEmpty()) ListUiState.Empty
                            else ListUiState.Success(posts.filter { it.title.contains(query) })
                    }
                    .onFailure { e ->
                        homeUiState = ListUiState.Error(e)
                    }
            }
        }
    }

    /**
     * upload a list of sample posts to firestore for app demonstration purpose
     */
    fun uploadSamplePosts(context: Context){
        viewModelScope.launch {
            uploadSamplePosts(context){ post -> postRepository.addPost(post) } }
    }


    init {
//    throw RuntimeException("Test Crash") // Force a crash
        log.d("HomeFeedViewModel: init")

        // Fetch posts from the repository
        loadPosts()
    }
}