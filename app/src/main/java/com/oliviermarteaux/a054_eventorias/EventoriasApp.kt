package com.oliviermarteaux.a054_eventorias

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.oliviermarteaux.localshared.composables.startup.RequestCameraPermission
import com.oliviermarteaux.localshared.composables.startup.RequestMapsPermission
import com.oliviermarteaux.localshared.ui.navigation.Screen
import com.oliviermarteaux.localshared.ui.navigation.SharedNavGraph
import com.oliviermarteaux.shared.composables.startup.DismissKeyboardOnTapOutside

@Composable
fun EventoriasApp(){

    RequestMapsPermission()
    RequestCameraPermission()
    DismissKeyboardOnTapOutside { SharedNavGraph(
        navHostController = rememberNavController(),
        startDestination = Screen.Splash.route,
        logoRes = R.drawable.eventorias_logo
    ) }
//    LogRoute()
}