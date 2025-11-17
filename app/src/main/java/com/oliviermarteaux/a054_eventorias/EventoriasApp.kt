package com.oliviermarteaux.a054_eventorias

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.oliviermarteaux.localshared.ui.navigation.Screen
import com.oliviermarteaux.localshared.ui.navigation.SharedNavGraph
import com.oliviermarteaux.shared.composables.startup.DismissKeyboardOnTapOutside

@Composable
fun EventoriasApp(){

    DismissKeyboardOnTapOutside { SharedNavGraph(
        navHostController = rememberNavController(),
        startDestination = Screen.Splash.route,
        logoRes = R.drawable.eventorias_logo
    ) }
//    LogRoute()
}