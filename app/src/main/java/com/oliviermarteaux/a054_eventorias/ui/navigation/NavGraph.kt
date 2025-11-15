package com.oliviermarteaux.a054_eventorias.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.login.LoginScreen
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.splash.SplashScreen
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.a054_eventorias.ui.screen.password.PasswordScreen

/**
 * The main navigation graph for the application.
 *
 * @param navHostController The navigation controller for the application.
 */
@Composable
fun EventoriasNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Splash.route
    ) {
        val logoDrawableRes = R.drawable.eventorias_logo
        /*_ SPLASH SCREEN ############################################################################*/
        composable(route = Screen.Splash.route) {
            SplashScreen(
                logoDrawableRes = logoDrawableRes,
                navigateToLoginScreen = { navHostController.navigate(Screen.Login.route) }
            )
        }
        /*_ LOGIN SCREEN #############################################################################*/
        composable(route = Screen.Login.route) {
            LoginScreen(
                logoDrawableRes = logoDrawableRes,
                onBackClick = { navHostController.navigateUp() },
                navigateToPasswordScreen = {
                        email -> navHostController.navigate("password/$email")
                },
                navigateToHomeScreen = {
//                    navHostController.navigate(Screen.Homefeed.route)
                }
            )
        }
        /*_ PASSWORD SCREEN ##########################################################################*/
        composable(
            route = Screen.Password.routeWithArgs,
            arguments = Screen.Password.navArguments
        ) { backStackEntry ->
            PasswordScreen(
                logoDrawableRes = logoDrawableRes,
                onBackClick = { navHostController.navigateUp() },
                navigateToHomeScreen = {
//                    navHostController.navigate(Screen.Homefeed.route){
//                    popUpTo(0) { inclusive = true } // clear everything
//                }
                                       },
                navigateToPasswordResetScreen = {
//                        email -> navHostController.navigate(Screen.Reset.route + "/${email}")
                }
            )
        }
//        /*_ RESET SCREEN #############################################################################*/
//        composable(
//            route = Screen.Reset.route+ "/{email}",
//            arguments = listOf(navArgument("email") { type = NavType.StringType })
//        ) { backStackEntry ->
//            ResetScreen(
//                onBackClick = { navHostController.navigateUp() },
//                navigateToLoginScreen = { navHostController.navigate(Screen.Login.route) },
//            )
//        }
//        /*_ HOME SCREEN ##############################################################################*/
//        composable(route = Screen.Homefeed.route) {
//            HomeFeedScreen(
//                onPostClick = {
//                        post -> navHostController.navigate(Screen.Detail.route + "/${post.id}")
//                },
//                onSettingsClick = { navHostController.navigate(Screen.Settings.route) },
//                navigateToLogin = { navHostController.navigate(Screen.Login.route) },
//                navigateToAccount = { navHostController.navigate(Screen.Account.route) },
//                navigateToAddPost = { navHostController.navigate(Screen.AddPost.route) }
//            )
//        }/*_ DETAIL SCREEN ###########################################################################*/
//        composable(
//            route = Screen.Detail.route + "/{post_id}",
//            arguments = listOf(navArgument("post_id") { type = NavType.StringType })
//        ){
//            DetailScreen(
//                onBackClick = { navHostController.navigateUp() },
//                navigateToCommentScreen = {
//                        post -> navHostController.navigate(Screen.Comment.route + "/${post.id}")
//                }
//            )
//        }
//        /*_ COMMENT SCREEN ###########################################################################*/
//        composable(
//            route = Screen.Comment.route + "/{post_id}",
//            arguments = listOf(navArgument("post_id") { type = NavType.StringType })
//        ){
//            CommentScreen(onBackClick = { navHostController.navigateUp() })
//        }
//        /*_ ACCOUNT SCREEN ###########################################################################*/
//        composable(route = Screen.Account.route) {
//            AccountScreen(navigateBack = { navHostController.navigateUp() })
//        }
//        /*_ ADD POST SCREEN ##########################################################################*/
//        composable(route = Screen.AddPost.route) {
//            AddScreen(navigateBack = { navHostController.navigateUp() })
//        }
//        /*_ SETTINGS SCREEN ##########################################################################*/
//        composable(route = Screen.Settings.route) {
//            SettingsScreen(onBackClick = { navHostController.navigateUp() })
//        }
    }
}