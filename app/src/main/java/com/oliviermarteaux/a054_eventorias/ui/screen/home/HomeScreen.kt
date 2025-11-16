package com.oliviermarteaux.a054_eventorias.ui.screen.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.a054_eventorias.ui.navigation.Screen
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.shared.composables.CenteredCircularProgressIndicator
import com.oliviermarteaux.shared.composables.SharedCardAsyncImage
import com.oliviermarteaux.shared.composables.SharedScaffold
import com.oliviermarteaux.shared.composables.SharedToast
import com.oliviermarteaux.shared.composables.texts.TextTitleMedium
import com.oliviermarteaux.shared.composables.texts.TextTitleSmall
import com.oliviermarteaux.shared.ui.ListUiState
import com.oliviermarteaux.shared.ui.theme.SharedPadding
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext

/**
 * A screen that displays a feed of posts.
 *
 * @param modifier The modifier to apply to this screen.
 * @param viewModel The view model for this screen.
 * @param onPostClick A function to call when a post is clicked.
 * @param onSettingsClick A function to call when the settings button is clicked.
 * @param navigateToLogin A function to call to navigate to the login screen.
 * @param navigateToAccount A function to call to navigate to the account screen.
 * @param navigateToAddPost A function to call to navigate to the add post screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onPostClick: (Post) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
    navigateToAccount: () -> Unit = {},
    navigateToAddPost: () -> Unit = {}
) {
    val context = LocalContext.current

    with(viewModel) {
        SharedScaffold(
            title = stringResource(Screen.Home.titleRes),
            onMenuItem1Click = onSettingsClick,
            menuItem1Title = stringResource(id = R.string.menu_action_settings),
            onMenuItem2Click = {
                checkUserState(
                    onUserLogged = navigateToAccount,
                    onNoUserLogged = navigateToLogin
                )
            },
            menuItem2Title = stringResource(id = R.string.menu_action_my_account),
            onFabClick = {
                uploadSamplePosts(context)
//                checkUserState(
//                    onUserLogged = navigateToAddPost,
//                    onNoUserLogged = ::showAuthErrorToast
//                )
            }
        ) { contentPadding ->
            LaunchedEffect(homeUiState) {
                Log.i(
                    "OM_TAG",
                    "HomeFeedViewModel: LaunchedEffect: homeFeedUiState = $homeUiState"
                )
            }
            Box {
                //_ UiState management: Empty, Error, Loading, Success
                when (homeUiState) {
                    is ListUiState.Loading -> CenteredCircularProgressIndicator()
                    is ListUiState.Empty -> SharedToast(stringResource(R.string.home_screen_empty_state))
                    is ListUiState.Error -> {
                        SharedToast(
                            text = stringResource(R.string.application_error_unknown),
                            bottomPadding = 200
                        )
                    }

                    is ListUiState.Success -> {
                        HomeFeedList(
                            modifier = modifier.padding(contentPadding).padding(horizontal = SharedPadding.medium),
                            posts = (homeUiState as ListUiState.Success<Post>).data,
                            onPostClick = onPostClick
                        )
                    }
                }
                if (authError) SharedToast(
                    text = stringResource(R.string.home_screen_error_no_user_logged),
                    bottomPadding = 120
                )
                if (networkError) SharedToast(
                    text = stringResource(R.string.application_error_network),
                    bottomPadding = 160
                )
            }
        }
    }
}

/**
 * A composable that displays a list of posts.
 *
 * @param modifier The modifier to apply to this composable.
 * @param posts The list of posts to display.
 * @param onPostClick A function to call when a post is clicked.
 */
@Composable
private fun HomeFeedList(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onPostClick: (Post) -> Unit,
) {
    LazyColumn(
        modifier = modifier/*.padding(8.dp)*/,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(posts) { post ->
            HomeFeedCell(
                post = post,
                onPostClick = onPostClick
            )
        }
    }
}

/**
 * A composable that displays a single post in the home feed.
 *
 * @param post The post to display.
 * @param onPostClick A function to call when the post is clicked.
 */
@Composable
private fun HomeFeedCell(
    post: Post,
    onPostClick: (Post) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onPostClick(post)
        }) {
        Column(
            modifier = Modifier.padding(SharedPadding.medium),
        ) {
            TextTitleSmall(
                text = stringResource(
                    id = R.string.application_by_author,
                    post.author?.firstname ?: "",
                    post.author?.lastname ?: ""
                ),
                modifier = Modifier.padding(bottom = SharedPadding.xs)
            )
            TextTitleMedium(text = post.title)
            Spacer(Modifier.padding(SharedPadding.small))
            if (!post.photoUrl.isNullOrEmpty()) {
                SharedCardAsyncImage(
                    photoUri = post.photoUrl,
                    modifier = Modifier
                        .padding(bottom = SharedPadding.xs)
                        .heightIn(max = 200.dp),
                    imageModifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 16 / 9f),
                )
            }
            if (!post.description.isNullOrEmpty()) {
                Spacer(Modifier.padding(SharedPadding.small))
                Text(
                    text = post.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}