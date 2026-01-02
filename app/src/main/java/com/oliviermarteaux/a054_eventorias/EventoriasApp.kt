package com.oliviermarteaux.a054_eventorias

import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.oliviermarteaux.a054_eventorias.ui.navigation.SharedNavGraph
import com.oliviermarteaux.shared.composables.startup.DismissKeyboardOnTapOutside
import com.oliviermarteaux.shared.composables.startup.RequestCameraPermission
import com.oliviermarteaux.shared.composables.startup.RequestMapsPermission
import com.oliviermarteaux.shared.composables.startup.RequestNotificationPermission
import com.oliviermarteaux.shared.navigation.LogRoutes
import com.oliviermarteaux.shared.navigation.Screen

@Composable
fun EventoriasApp(){

    val navController = rememberNavController()

    Log.d("OM_TAG", "BuildConfig: Debug = ${BuildConfig.DEBUG}")

    val startDestination: String =
        if (BuildConfig.DEBUG) {
            Log.d("OM_TAG", "start screen = ${Screen.Home.route}")
            Screen.Home.route
        } else {
            Log.d("OM_TAG", "start screen = ${Screen.Splash.route}")
            Screen.Splash.route
        }

    if (!BuildConfig.DEBUG) {
        RequestMapsPermission()
        RequestCameraPermission()
        RequestNotificationPermission()
    }

    Surface(){
        DismissKeyboardOnTapOutside {
            SharedNavGraph(
                navHostController = navController,
                startDestination = startDestination,
                logoRes = R.drawable.eventorias_logo
            )
        }
    }

    LogRoutes(navController)
}