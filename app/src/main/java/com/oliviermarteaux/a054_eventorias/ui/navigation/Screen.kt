package com.oliviermarteaux.a054_eventorias.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.oliviermarteaux.a054_eventorias.R

/**
 * A sealed class that represents the different screens in the application.
 *
 * @property route The route for the screen.
 * @property navArguments The navigation arguments for the screen.
 */
sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
    val routeWithArgs: String = "",
    val titleRes: Int = -1,
) {
    /**
     * The splash screen.
     */
    data object Splash : Screen("splash")

    /**
     * The login screen.
     */
    data object Login : Screen("login")

    /**
     * The password screen.
     */
    data object Password : Screen(
        route = "password",
        navArguments = listOf(navArgument("email") { type = NavType.StringType }),
        routeWithArgs = "password/{email}"
    )
    /**
     * The reset screen.
     */
    data object Reset : Screen(
        route = "reset",
        navArguments = listOf(navArgument("email") { type = NavType.StringType }),
        routeWithArgs = "reset/{email}"
    )

    /**
     * The home screen.
     */
    data object Home : Screen(
        route = "home",
        titleRes = R.string.home_screen_title
    )

    /**
     * The account screen.
     */
    data object Account : Screen(
        route = "account",
        titleRes = R.string.account_screen_title
    )
}