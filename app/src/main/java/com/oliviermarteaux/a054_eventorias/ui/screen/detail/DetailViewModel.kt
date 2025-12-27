package com.oliviermarteaux.a054_eventorias.ui.screen.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.oliviermarteaux.shared.firebase.firestore.data.repository.PostRepository
import com.oliviermarteaux.shared.firebase.firestore.domain.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the Detail screen.
 *
 * @param savedStateHandle The saved state handle for the view model.
 * @param postRepository The repository for managing posts.
 * @param userRepository The repository for managing user data.
 * @param log The logger.
 * @param isOnlineFlow A flow that emits the current internet connection status.
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val postRepository: PostRepository,
): ViewModel() {
    /**
     * The post to display.
     */
    var post: Post by mutableStateOf(Post())
        private set
}