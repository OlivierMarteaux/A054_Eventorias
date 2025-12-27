package com.oliviermarteaux.a054_eventorias.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.oliviermarteaux.a054_eventorias.ui.screen.account.AccountScreen
import com.oliviermarteaux.a054_eventorias.ui.screen.add.AddScreen
import com.oliviermarteaux.a054_eventorias.ui.screen.detail.DetailScreen
import com.oliviermarteaux.a054_eventorias.ui.screen.home.HomeScreen
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.login.LoginScreen
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.password.PasswordScreen
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.reset.ResetScreen
import com.oliviermarteaux.localshared.firebase.authentication.ui.screen.splash.SplashScreen
import com.oliviermarteaux.localshared.firebase.firestore.ui.PostViewModel
import com.oliviermarteaux.shared.cameraX.CameraScreen
import com.oliviermarteaux.shared.navigation.Screen

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
    postViewModel: PostViewModel = hiltViewModel()
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
                navigateToHomeScreen = { navHostController.navigate(Screen.Home.route) }
            )
        }
        /*_ PASSWORD SCREEN ##########################################################################*/
        composable(
            route = Screen.Password.routeWithArgs,
            arguments = Screen.Password.navArguments
        ) {
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
        ) {
            ResetScreen(
                onBackClick = { navHostController.navigateUp() },
                navigateToLoginScreen = { navHostController.navigate(Screen.Login.route) },
            )
        }
        /*_ HOME SCREEN ##############################################################################*/
        composable(route = Screen.Home.route) {
            HomeScreen(
                postViewModel = postViewModel,
                navController = navHostController,
                navigateToDetailScreen = {navHostController.navigate(Screen.Detail.route) },
                navigateToAddScreen = { navHostController.navigate(Screen.Add.route) }
            )
        }
        /*_ DETAIL SCREEN ###########################################################################*/
        composable(route = Screen.Detail.route){
            DetailScreen(
                postViewModel = postViewModel,
                onBackClick = { navHostController.navigateUp() },
            )
        }
        /*_ ACCOUNT SCREEN ###########################################################################*/
        composable(route = Screen.Account.route) {
            AccountScreen(
                navController = navHostController
            )
        }
        /*_ ADD POST SCREEN ##########################################################################*/
        composable(route = Screen.Add.route) {
            AddScreen(
                navigateBack = { navHostController.navigateUp() },
                navigateToCamera = { navHostController.navigate(Screen.Camera.route) }
            )
        }
        /*_ CAMERA SCREEN ##########################################################################*/
        composable(route = Screen.Camera.route) {
            CameraScreen(
                navigateBack = { navHostController.navigateUp() },
            )
        }
    }
}