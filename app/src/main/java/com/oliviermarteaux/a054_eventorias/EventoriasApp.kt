package com.oliviermarteaux.a054_eventorias

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
fun EventoriasApp(
    startDestination: String = Screen.Splash.route
){

    val navController = rememberNavController()

    RequestMapsPermission()
    RequestCameraPermission()
    RequestNotificationPermission()

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