package com.oliviermarteaux.a054_eventorias.ui.screen.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.oliviermarteaux.localshared.cameraX.CameraRepository
import com.oliviermarteaux.localshared.extensions.toDateTypeDate
import com.oliviermarteaux.localshared.extensions.toDateTypeTime
import com.oliviermarteaux.localshared.firebase.authentication.data.repository.UserRepository
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.AuthUserViewModel
import com.oliviermarteaux.localshared.firebase.firestore.data.repository.PostRepository
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Address
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.shared.ui.UiState
import com.oliviermarteaux.shared.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * This ViewModel manages data and interactions related to adding new posts in the AddScreen.
 * It utilizes dependency injection to retrieve a PostRepository instance for interacting with post data.
 */
@HiltViewModel
class AddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val cameraRepository: CameraRepository,
    private val isOnlineFlow: Flow<Boolean>,
    private val log: Logger
) : AuthUserViewModel(
    userRepository = userRepository,
    isOnlineFlow = isOnlineFlow,
    log = log
) {
    var addPostUiState: UiState<Unit> by mutableStateOf(UiState.Idle)
        private set

    var post: Post by mutableStateOf(Post())
        private set

    var photoUrl: String? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            cameraRepository.photoUrl = null
            snapshotFlow { cameraRepository.photoUrl }
                .stateIn ( scope = viewModelScope )
                .collect {
                    photoUrl = it
                    post = post.copy(photoUrl = it)
                }
        }
    }

    fun updatePostTitle(title: String) { post = post.copy(title = title) }
    fun updatePostDescription(description: String) { post = post.copy(description = description) }
    fun updatePostDate(date: String) { post = post.copy(date = date.toDateTypeDate()) }
    fun updatePostTime(time: String) { post = post.copy(time = time.toDateTypeTime()) }
    fun updatePostAddress(address: String) { post = post.copy(address = Address(address)) }
    fun updatePostPhoto(photoUrl: String) { post = post.copy(photoUrl = photoUrl) }

    /**
     * Attempts to add the current post to the repository after setting the author.
     *
     * TODO: Implement logic to retrieve the current user.
     */
    fun addPost(onResult: () -> Unit) {
        addPostUiState = UiState.Loading
        if(!isOnline) {
            showNetworkErrorToast()
            addPostUiState = UiState.Idle // 🟢 reset state since we're not adding the post
            return
        }
        //_ add the post to the repository
        viewModelScope.launch(Dispatchers.IO) {
//      delay(3000) // simulate network delay for Loading state evidence
            postRepository.addPost(post.copy(author = currentUser)).fold(
                onSuccess = { withContext(Dispatchers.Main) { onResult() } },
                onFailure = { showUnknownErrorToast() }
            )
            addPostUiState = UiState.Idle
        }
    }
//    init { post.copy(photoUrl = null) }
}