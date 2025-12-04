package com.oliviermarteaux.localshared.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.composables.extensions.cdButtonSemantics
import com.oliviermarteaux.localshared.ui.theme.SharedPadding
import com.oliviermarteaux.shared.composables.IconSource
import com.oliviermarteaux.shared.composables.SharedAsyncImage
import com.oliviermarteaux.shared.composables.SharedIcon
import com.oliviermarteaux.shared.composables.SharedIconButton
import com.oliviermarteaux.shared.composables.texts.TextTitleLarge
import com.oliviermarteaux.shared.composables.texts.TextTitleSmall

/**
 * A reusable scaffold composable with a top app bar, optional floating action button (FAB),
 * and optional menu actions. This composable wraps [Scaffold] from Material 3 and provides
 * a consistent structure for screens across the app.
 *
 * ### Features:
 * - Top app bar with customizable title, optional back button, and menu actions.
 * - Optional FAB with configurable appearance, behavior, and content description.
 * - Supports up to two menu items in a dropdown menu.
 * - Provides content area with [contentPadding] automatically applied.
 *
 * ### Behavior:
 * - If [onBackClick] is provided, a back navigation icon is displayed in the top app bar.
 * - If [onMenuItem1Click] is provided, a menu icon is displayed; optionally supports a second menu item.
 * - If [onFabClick] is provided, a floating action button is displayed with the standard "Add" icon.
 * - Menu and FAB are disabled if their respective callbacks are `null` or [fabEnabled] is `false`.
 *
 * ### Parameters:
 * @param modifier [Modifier] applied to the scaffold.
 * @param title The text displayed as the top app bar title.
 * @param topAppBarModifier [Modifier] applied to the top app bar.
 * @param onFabClick Optional lambda invoked when the FAB is clicked.
 * @param onBackClick Optional lambda invoked when the back button is clicked.
 * @param onMenuItem1Click Optional lambda for the first menu item click.
 * @param onMenuItem2Click Optional lambda for the second menu item click.
 * @param menuItem1Title Title text for the first menu item.
 * @param menuItem2Title Title text for the second menu item.
 * @param fabEnabled Controls whether the FAB is clickable. Defaults to `true`.
 * @param fabShape Shape of the FAB. Defaults to [FloatingActionButtonDefaults.shape].
 * @param fabContainerColor Background color of the FAB. Defaults to [FloatingActionButtonDefaults.containerColor].
 * @param fabContentColor Content color of the FAB. Defaults to [contentColorFor(fabContainerColor)].
 * @param fabInteractionSource Optional [MutableInteractionSource] for the FAB.
 * @param fabContentDescription Accessibility content description for the FAB.
 * @param bottomBar The composable to be used as the bottom app bar.
 * @param content Composable lambda representing the main content of the scaffold. Receives [contentPadding] to account for system bars and FAB.
 *
 * ### Example Usage:
 * ```kotlin
 * @Composable
 * fun ExampleScreen() {
 *     SharedScaffold(
 *         title = "Dashboard",
 *         onBackClick = { /* navigate back */ },
 *         onFabClick = { /* add item */ },
 *         onMenuItem1Click = { /* menu action */ },
 *         menuItem1Title = "Settings",
 *     ) { padding ->
 *         Column(modifier = Modifier.padding(padding)) {
 *             Text("Hello World")
 *         }
 *     }
 * }
 * ```
 *
 * @see Scaffold
 * @see TopAppBar
 * @see FloatingActionButton
 * @see DropdownMenu
 * @see SharedIconButton
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedScaffold(
    modifier: Modifier = Modifier,
    //_ topAppBar
    title: String = "",
    screenContentDescription: String = "",
    topAppBarModifier: Modifier = Modifier,
    trailingIcon: IconSource? = null,
    avatarUrl: String? = null,
    onBackClick: (() -> Unit)? = null,
    //_ search function
    query: String = "",
    onQueryChange: ((String) -> Unit)? = {},
    onSearchBarIconClick: (() -> Unit) = {},
    searchBarModifier: Modifier = Modifier,
    searchLabel: String = "",
    //_ sort function
    onSortByTitleClick: (() -> Unit)? = null,
    onSortByAscendingDateClick: (() -> Unit)? = null,
    onSortByDescendingDateClick: (() -> Unit)? = null,
    //_ menu function
    onMenuItem1Click: (() -> Unit)? = null,
    onMenuItem2Click: (() -> Unit)? = null,
    menuItem1Title: String = "",
    menuItem2Title: String = "",
    //_ fab function
    onFabClick: (() -> Unit)? = null,
    fabVisible: Boolean = true,
    fabEnabled: Boolean = true,
    fabShape: Shape =  FloatingActionButtonDefaults.shape,
    fabContainerColor: Color =  FloatingActionButtonDefaults.containerColor,
    fabContentColor: Color = contentColorFor(fabContainerColor),
    fabInteractionSource: MutableInteractionSource? = null,
    fabContentDescription: String = "",
    fabModifier: Modifier = Modifier,
    fabIconTint: Color = contentColorFor(fabContainerColor),
    //_ access fab button
    accessFabButton: Boolean? = null,
    //_ bottom bar
    bottomBar: @Composable () -> Unit = {},
    //_ content
    content: @Composable (contentPadding: PaddingValues) -> Unit = {},
){
    var menuDisplayed by rememberSaveable { mutableStateOf(false) }
    var sortOptionsDisplayed by rememberSaveable { mutableStateOf(false) }
    var searchBarDisplayed by rememberSaveable { mutableStateOf(false) }

    fun showMenu(){ menuDisplayed = true }
    fun hideMenu(){ menuDisplayed = false }
    fun showSortOptions(){ sortOptionsDisplayed = true }
    fun hideSortOptions(){ sortOptionsDisplayed = false }
    fun toggleSearchBar(){ searchBarDisplayed = !searchBarDisplayed }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        AnimatedVisibility(
                            visible = !searchBarDisplayed,
                            enter = expandHorizontally(),
                            exit = shrinkHorizontally()
                        ) {
                            TextTitleLarge(
                                text = title,
                                modifier = modifier.clearAndSetSemantics(
                                    properties = {
                                        contentDescription = screenContentDescription
                                    }
                                )
                            )
                        }
                    }
                },
                modifier = topAppBarModifier.height(125.dp),
                navigationIcon = {
                    onBackClick?.let {
                        SharedIconButton(
                            icon = IconSource.VectorIcon(Icons.AutoMirrored.Filled.ArrowBack),
                        ) { onBackClick() }
                    }
                },
                actions = {
                    avatarUrl?.let {
                        SharedAsyncImage(
                            photoUri = avatarUrl,
                            modifier = Modifier
                                .padding(end = SharedPadding.small)
                                .size(48.dp)
                                .clip(shape = CircleShape)
                        )
                    }
                    trailingIcon?.let {
                        SharedIcon(
                            icon  =  trailingIcon,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(shape = CircleShape)
                        )
                    }
                    onQueryChange?.let {
                        AnimatedVisibility(
                            visible = searchBarDisplayed,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = SharedPadding.small),
//                            enter = expandHorizontally(),
//                            exit = shrinkHorizontally()
                        ) {
                            val searchBarFocusRequester = remember { FocusRequester() }
                            LaunchedEffect(Unit) { searchBarFocusRequester.requestFocus() }
                            val keyboardController = LocalSoftwareKeyboardController.current

                            SharedSearchBar(
                                query = query,
                                onQueryChange = onQueryChange,
                                modifier = searchBarModifier
                                    .focusRequester(searchBarFocusRequester)
                                    .fillMaxWidth(),
                                onSearch =  { keyboardController?.hide() },
                                searchBarIcon = IconSource.VectorIcon(Icons.Default.Clear),
                                searchLabel = searchLabel,
                                onSearchBarIconClick = onSearchBarIconClick
                            )
                        }
                        val cdSearchButton =
                            stringResource(R.string.search_button_double_tap_to_open_the_search_bar)
                        SharedIconButton(
                            icon = IconSource.VectorIcon(Icons.Default.Search),
                            modifier = Modifier.cdButtonSemantics(cdSearchButton)
                        ) {
                            toggleSearchBar();

                        }
                    }
                    onSortByTitleClick?.let{
                        val cdSortButton =
                            stringResource(R.string.sort_button_double_tap_to_open_the_sort_menu)
                        SharedIconButton(
                            icon = IconSource.VectorIcon(Icons.Default.SwapVert),
                            modifier = Modifier.cdButtonSemantics(cdSortButton)
                        ){ showSortOptions() }
                        DropdownMenu(
                            expanded = sortOptionsDisplayed,
                            onDismissRequest = { hideSortOptions() }
                        ) {
                            DropdownMenuItem(
                                text = { TextTitleSmall(text = "Ascending title") },
                                onClick = { onSortByTitleClick() },
                            )
                            onSortByAscendingDateClick?.let { DropdownMenuItem(
                                text = { TextTitleSmall(text = "Ascending date") },
                                onClick = { onSortByAscendingDateClick() },
                            )}
                            onSortByDescendingDateClick?.let { DropdownMenuItem(
                                text = { TextTitleSmall(text = "Descending date") },
                                onClick = { onSortByDescendingDateClick() },
                            )}
                        }
                    }
                    onMenuItem1Click?.let{
                        SharedIconButton(
                            icon = IconSource.VectorIcon(Icons.Default.MoreVert),
                        ) { showMenu() }
                        DropdownMenu(
                            expanded = menuDisplayed,
                            onDismissRequest = { hideMenu() }
                        ) {
                            DropdownMenuItem(
                                text = { TextTitleSmall(text = menuItem1Title) },
                                onClick = {
                                    onMenuItem1Click()
                                    hideMenu()
                                },
                            )
                            onMenuItem2Click?.let {
                                DropdownMenuItem(
                                    text = { TextTitleSmall(text = menuItem2Title) },
                                    onClick = {
                                        onMenuItem2Click()
                                        hideMenu()
                                    },
                                )
                            }
                        }
                    }
//                    accessFabButton?.let{
//                        SharedIconButton(
//                            icon = IconSource.VectorIcon(Icons.Filled.Add),
//                            tint = fabIconTint,
//                            colors = IconButtonColors(
//                                containerColor = fabContainerColor,
//                                contentColor = fabContentColor,
//                                disabledContainerColor = fabContainerColor,
//                                disabledContentColor = fabContentColor
//                            ),
//                            shape = fabShape,
//                            modifier = Modifier
//                                .padding(end = SharedPadding.xs)
//                                .cdSemantics(fabContentDescription)
//                                .size(SharedSize.xs),
//                            onClick = onFabClick?:{}
//                        )
//                    }
                }
            )
        },
        bottomBar = bottomBar,
        floatingActionButton = {
            onFabClick?.let {
                if (fabVisible){
                    FloatingActionButton(
                        onClick = { if (fabEnabled) onFabClick() },
                        modifier = fabModifier
                            .cdButtonSemantics(fabContentDescription),
                        shape = fabShape,
                        containerColor = fabContainerColor,
                        contentColor = fabContentColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(10.dp),
                        interactionSource = fabInteractionSource
                    ) {
                        SharedIcon(
                            icon = IconSource.VectorIcon(Icons.Filled.Add),
                            tint = fabIconTint
                        )
                    }
                }
            }
        },
    ) { contentPadding -> content(contentPadding) }
}
