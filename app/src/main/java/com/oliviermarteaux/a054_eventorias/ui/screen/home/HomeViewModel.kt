package com.oliviermarteaux.a054_eventorias.ui.screen.home

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.oliviermarteaux.localshared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.AuthUserViewModel
import com.oliviermarteaux.localshared.firebase.firestore.data.repository.PostRepository
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.localshared.firebase.firestore.utils.uploadSamplePosts
import com.oliviermarteaux.shared.ui.ListUiState
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private var posts: List<Post> by mutableStateOf(emptyList())

    var filteredPosts: List<Post> by mutableStateOf(emptyList())
        private set

    var currentSortOption: SortOption? by mutableStateOf(null)
        private set

    var query: String by mutableStateOf("")
        private set

    var fabVisible: Boolean by mutableStateOf(false)
        private set

    fun updateQuery(newQuery: String) {
        query = newQuery
    }
    fun clearQuery(){
        query = ""
        filterPosts("")
    }

//    var searchBarVisible: Boolean by mutableStateOf(false)
//        private set
//    fun showSearchBar() { searchBarVisible = true }
//    fun hideSearchBar() { searchBarVisible = false }

//    fun toggleSearchBar(){
//        searchBarVisible = !searchBarVisible
//    }

    fun filterPosts(query: String) {
        filteredPosts = posts.filter { post ->
            listOfNotNull(post.title, post.author?.firstname, post.author?.lastname)
                .any { field -> field.contains(query, true) }
        }.sortedWith ( currentSortOption?.comparator?:compareBy { null } )
    }

    fun sortPostsBy(sortOption: SortOption) {
        currentSortOption = sortOption
        filteredPosts = filteredPosts.sortedWith(sortOption.comparator)
    }

    /**
     * Loads the posts from the repository.
     */
    fun loadPosts() {
        viewModelScope.launch {
            homeUiState = ListUiState.Loading
//            delay(1500) // simulate network delay for Loading state evidence
            postRepository.posts.collect { result ->
                result
                    .onSuccess {
                        fabVisible = true
                        posts = it
                        filteredPosts = it
                        homeUiState =
                            if (posts.isEmpty()) ListUiState.Empty
                            else ListUiState.Success(posts)
                    }
                    .onFailure { e ->
                        homeUiState = ListUiState.Error(e)
                        fabVisible = false
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
        setAuthObserverDelay(2000)
//    throw RuntimeException("Test Crash") // Force a crash
        log.d("HomeFeedViewModel: init")

        // Fetch posts from the repository
        loadPosts()
    }
}