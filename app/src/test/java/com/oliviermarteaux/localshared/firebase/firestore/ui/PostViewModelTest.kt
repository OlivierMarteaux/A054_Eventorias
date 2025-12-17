package com.oliviermarteaux.localshared.firebase.firestore.ui

import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PostViewModelTest {

    private lateinit var viewModel: PostViewModel

    @Before
    fun setup() {
        viewModel = PostViewModel()
    }

    @Test
    fun selectPost_whenCalled_shouldUpdatePostState() {
        // Given
        val selectedPost = Post(
            id = "post-id",
            title = "Compose Accessibility"
        )

        // When
        viewModel.selectPost(selectedPost)

        // Then
        assertEquals(selectedPost, viewModel.post)
    }

    @Test
    fun selectPost_whenCalledMultipleTimes_shouldExposeLastSelectedPost() {
        // Given
        val firstPost = Post(id = "1", title = "First")
        val secondPost = Post(id = "2", title = "Second")

        // When
        viewModel.selectPost(firstPost)
        viewModel.selectPost(secondPost)

        // Then
        assertEquals(secondPost, viewModel.post)
    }
}