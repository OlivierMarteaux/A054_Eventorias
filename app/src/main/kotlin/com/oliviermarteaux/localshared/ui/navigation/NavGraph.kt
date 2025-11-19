package com.oliviermarteaux.localshared.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.oliviermarteaux.a054_eventorias.ui.screen.account.AccountScreen
import com.oliviermarteaux.a054_eventorias.ui.screen.add.AddScreen
import com.oliviermarteaux.a054_eventorias.ui.screen.detail.DetailScreen
import com.oliviermarteaux.a054_eventorias.ui.screen.home.HomeScreen
import com.oliviermarteaux.localshared.cameraX.CameraScreen
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.login.LoginScreen
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.password.PasswordScreen
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.reset.ResetScreen
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.splash.SplashScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * The main navigation graph for the application.
 *
 * @param navHostController The navigation controller for the application.
 */

@Composable
fun SharedNavGraph(
    navHostController: NavHostController,
    startDestination: String,
    logoRes: Int = -1,
){
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        /*_ SPLASH SCREEN ############################################################################*/
        composable(route = Screen.Splash.route) {
            SplashScreen(
                logoDrawableRes = logoRes,
                navigateToLoginScreen = { navHostController.navigate(Screen.Login.route) },
                navigateToHomeScreen = { navHostController.navigate(Screen.Home.route) }
            )
        }
        /*_ LOGIN SCREEN #############################################################################*/
        composable(route = Screen.Login.route) {
            LoginScreen(
                logoDrawableRes = logoRes,
                onBackClick = { navHostController.navigateUp() },
                navigateToPasswordScreen = {
                        email -> navHostController.navigate("password/$email")
                },
                navigateToHomeScreen = {
                    navHostController.navigate(Screen.Home.route)
                }
            )
        }
        /*_ PASSWORD SCREEN ##########################################################################*/
        composable(
            route = Screen.Password.routeWithArgs,
            arguments = Screen.Password.navArguments
        ) { backStackEntry ->
            PasswordScreen(
                logoDrawableRes = logoRes,
                onBackClick = { navHostController.navigateUp() },
                navigateToHomeScreen = {
                    navHostController.navigate(Screen.Home.route){
                        popUpTo(0) { inclusive = true } // clear everything
                    }
                },
                navigateToPasswordResetScreen = {
                        email -> navHostController.navigate(Screen.Reset.route + "/${email}")
                }
            )
        }
        /*_ RESET SCREEN #############################################################################*/
        composable(
            route = Screen.Reset.routeWithArgs,
            arguments = Screen.Reset.navArguments,
        ) { backStackEntry ->
            ResetScreen(
                onBackClick = { navHostController.navigateUp() },
                navigateToLoginScreen = { navHostController.navigate(Screen.Login.route) },
            )
        }
        /*_ HOME SCREEN ##############################################################################*/
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navHostController,
                navigateToDetailScreen = {
                        post -> navHostController.navigate(Screen.Detail.route + "/${post.id}")
                },
                onSettingsClick = { /*navHostController.navigate(Screen.Settings.route)*/ },
                navigateToLoginScreen = { /*navHostController.navigate(Screen.Login.route)*/ },
                navigateToAccountScreen = { /*navHostController.navigate(Screen.Account.route)*/ },
                navigateToAddScreen = {
                    navHostController.navigate(Screen.Add.route)
//                    navHostController.navigate(Screen.Add.route + "/")

                }
            )
        }
        /*_ DETAIL SCREEN ###########################################################################*/
        composable(
            route = Screen.Detail.route + "/{post_id}",
            arguments = listOf(navArgument("post_id") { type = NavType.StringType })
        ){
            DetailScreen(
                onBackClick = { navHostController.navigateUp() },
            )
        }
//        /*_ COMMENT SCREEN ###########################################################################*/
//        composable(
//            route = Screen.Comment.route + "/{post_id}",
//            arguments = listOf(navArgument("post_id") { type = NavType.StringType })
//        ){
//            CommentScreen(onBackClick = { navHostController.navigateUp() })
//        }
        /*_ ACCOUNT SCREEN ###########################################################################*/
        composable(route = Screen.Account.route) {
            AccountScreen()
        }
        /*_ ADD POST SCREEN ##########################################################################*/
        composable(
            route = Screen.Add.route,
        ) {
            AddScreen(
                navigateBack = { navHostController.navigateUp() },
                navigateToCamera = { navHostController.navigate(Screen.Camera.route) }
            )
        }
        /*_ CAMERA SCREEN ##########################################################################*/
        composable(route = Screen.Camera.route) {
            CameraScreen(
                navigateBack = { navHostController.navigateUp() },
//                onImageCapture = { photoUrl ->
//                    val encodedPhotoUrl = URLEncoder.encode(photoUrl, StandardCharsets.UTF_8.toString())
//                    navHostController.navigate(Screen.Add.route + "/$encodedPhotoUrl")
//                }
            )
        }
    }
}
//@Composable
//fun EventoriasNavHost(navHostController: NavHostController) {
//    NavHost(
//        navController = navHostController,
//        startDestination = Screen.Splash.route
//    ) {
//        val logoDrawableRes = R.drawable.eventorias_logo
//        /*_ SPLASH SCREEN ############################################################################*/
//        composable(route = Screen.Splash.route) {
//            SplashScreen(
//                logoDrawableRes = logoDrawableRes,
//                navigateToLoginScreen = { navHostController.navigate(Screen.Login.route) },
//                navigateToHomeScreen = { navHostController.navigate(Screen.Home.route) }
//            )
//        }
//        /*_ LOGIN SCREEN #############################################################################*/
//        composable(route = Screen.Login.route) {
//            LoginScreen(
//                logoDrawableRes = logoDrawableRes,
//                onBackClick = { navHostController.navigateUp() },
//                navigateToPasswordScreen = {
//                        email -> navHostController.navigate("password/$email")
//                },
//                navigateToHomeScreen = {
//                    navHostController.navigate(Screen.Home.route)
//                }
//            )
//        }
//        /*_ PASSWORD SCREEN ##########################################################################*/
//        composable(
//            route = Screen.Password.routeWithArgs,
//            arguments = Screen.Password.navArguments
//        ) { backStackEntry ->
//            PasswordScreen(
//                logoDrawableRes = logoDrawableRes,
//                onBackClick = { navHostController.navigateUp() },
//                navigateToHomeScreen = {
//                    navHostController.navigate(Screen.Home.route){
//                    popUpTo(0) { inclusive = true } // clear everything
//                }
//                                       },
//                navigateToPasswordResetScreen = {
//                        email -> navHostController.navigate(Screen.Reset.route + "/${email}")
//                }
//            )
//        }
//        /*_ RESET SCREEN #############################################################################*/
//        composable(
//            route = Screen.Reset.routeWithArgs,
//            arguments = Screen.Reset.navArguments,
//        ) { backStackEntry ->
//            ResetScreen(
//                onBackClick = { navHostController.navigateUp() },
//                navigateToLoginScreen = { navHostController.navigate(Screen.Login.route) },
//            )
//        }
//        /*_ HOME SCREEN ##############################################################################*/
//        composable(route = Screen.Home.route) {
//            HomeScreen(
//                navController = navHostController,
//                navigateToDetailScreen = {
////                        post -> navHostController.navigate(Screen.Detail.route + "/${post.id}")
//                },
//                onSettingsClick = { /*navHostController.navigate(Screen.Settings.route)*/ },
//                navigateToLoginScreen = { /*navHostController.navigate(Screen.Login.route)*/ },
//                navigateToAccountScreen = { /*navHostController.navigate(Screen.Account.route)*/ },
//                navigateToAddScreen = { /*navHostController.navigate(Screen.AddPost.route)*/ }
//            )
//        }
////        /*_ DETAIL SCREEN ###########################################################################*/
////        composable(
////            route = Screen.Detail.route + "/{post_id}",
////            arguments = listOf(navArgument("post_id") { type = NavType.StringType })
////        ){
////            DetailScreen(
////                onBackClick = { navHostController.navigateUp() },
////                navigateToCommentScreen = {
////                        post -> navHostController.navigate(Screen.Comment.route + "/${post.id}")
////                }
////            )
////        }
////        /*_ COMMENT SCREEN ###########################################################################*/
////        composable(
////            route = Screen.Comment.route + "/{post_id}",
////            arguments = listOf(navArgument("post_id") { type = NavType.StringType })
////        ){
////            CommentScreen(onBackClick = { navHostController.navigateUp() })
////        }
//        /*_ ACCOUNT SCREEN ###########################################################################*/
//        composable(route = Screen.Account.route) {
//            AccountScreen()
//        }
////        /*_ ADD POST SCREEN ##########################################################################*/
////        composable(route = Screen.AddPost.route) {
////            AddScreen(navigateBack = { navHostController.navigateUp() })
////        }
////        /*_ SETTINGS SCREEN ##########################################################################*/
////        composable(route = Screen.Settings.route) {
////            SettingsScreen(onBackClick = { navHostController.navigateUp() })
////        }
//    }
//}