package com.oliviermarteaux.a054_eventorias.ui.screen.home

import android.R.attr.text
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.composables.SharedBottomAppBar
import com.oliviermarteaux.localshared.composables.SharedScaffold
import com.oliviermarteaux.localshared.composables.SharedSearchBar
import com.oliviermarteaux.localshared.firebase.firestore.domain.model.Post
import com.oliviermarteaux.localshared.ui.navigation.Screen
import com.oliviermarteaux.localshared.ui.theme.SharedPadding
import com.oliviermarteaux.shared.composables.CenteredCircularProgressIndicator
import com.oliviermarteaux.shared.composables.SharedAsyncImage
import com.oliviermarteaux.shared.composables.SharedCardAsyncImage
import com.oliviermarteaux.shared.composables.SharedToast
import com.oliviermarteaux.shared.composables.texts.TextTitleMedium
import com.oliviermarteaux.shared.composables.texts.TextTitleSmall
import com.oliviermarteaux.shared.ui.ListUiState

/**
 * A screen that displays a feed of posts.
 *
 * @param modifier The modifier to apply to this screen.
 * @param viewModel The view model for this screen.
 * @param navigateToDetailScreen A function to call when a post is clicked.
 * @param onSettingsClick A function to call when the settings button is clicked.
 * @param navigateToLoginScreen A function to call to navigate to the login screen.
 * @param navigateToAccountScreen A function to call to navigate to the account screen.
 * @param navigateToAddScreen A function to call to navigate to the add post screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigateToDetailScreen: (Post) -> Unit = {},
    onSettingsClick: () -> Unit = {},
    navigateToLoginScreen: () -> Unit = {},
    navigateToAccountScreen: () -> Unit = {},
    navigateToAddScreen: () -> Unit = {}
) {
    val context = LocalContext.current
    with(homeViewModel) {
        SharedScaffold(
            title = stringResource(Screen.Home.titleRes),
            // top app bar
            topAppBarModifier = Modifier.padding(horizontal = SharedPadding.medium),
            // search bar
            onSearchIconClick = ::showSearchBar,
            searchBarVisible = searchBarVisible,
            query = query,
            onQueryChange = { updateQuery(it) ; filterPosts(it)},
            onSearchBarIconClick = {clearQuery(); hideSearchBar()},
            searchBarModifier = Modifier.padding(horizontal = SharedPadding.small),
            // sort menu
            onSortByTitleClick = { sortPostsBy(SortOption.TITLE) },
            onSortByAscendingDateClick = { sortPostsBy(SortOption.DATE_ASCENDING) },
            onSortByDescendingDateClick = { sortPostsBy(SortOption.DATE_DESCENDING) },
            // bottom app bar
            bottomBar = { SharedBottomAppBar(navController) },
            // fab button
            onFabClick = {
                // for initial posts populating purpose
//                 uploadSamplePosts(context)
                checkUserState(
                    onUserLogged = navigateToAddScreen,
                    onNoUserLogged = ::showAuthErrorToast
                )
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
                    is ListUiState.Empty -> SharedToast(stringResource(R.string.no_posts))
                    is ListUiState.Error -> {
                        SharedToast(
                            text = stringResource(R.string.an_unknown_error_occurred),
                            bottomPadding = 200
                        )
                    }

                    is ListUiState.Success -> {
                        HomeFeedList(
                            modifier = modifier
                                .consumeWindowInsets(contentPadding)   // 👈 prevents double padding,
                                .fillMaxWidth()
                                .padding(contentPadding)
                                .padding(horizontal = SharedPadding.xl),
                            posts = filteredPosts, //(homeUiState as ListUiState.Success<Post>).data,
                            navigateToDetailScreen = navigateToDetailScreen,
                            searchBarVisible = searchBarVisible,
                            query = query,
                            updateQuery = { updateQuery(it) },
                            filterPosts = { filterPosts(it) },
                            clearQuery = { clearQuery() },
                            hideSearchBar = ::hideSearchBar
                        )
                    }
                }
                if (authError) SharedToast(
                    text = stringResource(R.string.an_account_is_mandatory_to_add_a_post),
                    bottomPadding = 120
                )
                if (networkError) SharedToast(
                    text = stringResource(R.string.network_error_check_your_internet_connection),
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
 * @param navigateToDetailScreen A function to call when a post is clicked.
 */
@Composable
private fun HomeFeedList(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    navigateToDetailScreen: (Post) -> Unit,
    searchBarVisible: Boolean = false,
    query: String = "",
    updateQuery: (String) -> Unit,
    filterPosts: (String) -> Unit,
    clearQuery: () -> Unit,
    hideSearchBar: () -> Unit
) {
    Column (modifier = modifier ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(SharedPadding.small),
        ) {
            items(posts) { post ->
                HomeFeedCell(
                    post = post,
                    navigateToDetailScreen = navigateToDetailScreen
                )
            }
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
    navigateToDetailScreen: (Post) -> Unit,
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        modifier = Modifier.fillMaxWidth().height(80.dp),
        onClick = { navigateToDetailScreen(post) }
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ){
            SharedAsyncImage(
                photoUri = post.author?.photoUrl,
                modifier = Modifier
                    .padding(start = SharedPadding.large)
                    .size(40.dp)
                    .clip(shape = CircleShape)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(SharedPadding.large),
            ) {
                TextTitleMedium(text = post.title)
                Spacer(Modifier.padding(SharedPadding.xs))

                TextTitleSmall(text = post.localeDateString)
            }
            if (!post.photoUrl.isNullOrEmpty()) {
                SharedCardAsyncImage(
                    photoUri = post.photoUrl,
                    imageModifier = Modifier
                        .aspectRatio(ratio = 136/80f),
                )
            }
        }
    }
}