package com.oliviermarteaux.a054_eventorias.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * A sealed class that represents the different screens in the application.
 *
 * @property route The route for the screen.
 * @property navArguments The navigation arguments for the screen.
 */
sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList(),
    val routeWithArgs: String = ""
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
}