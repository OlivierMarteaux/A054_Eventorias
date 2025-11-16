package com.oliviermarteaux.a054_eventorias

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.oliviermarteaux.a054_eventorias.ui.navigation.EventoriasNavHost
import com.oliviermarteaux.shared.composables.startup.DismissKeyboardOnTapOutside

@Composable
fun EventoriasApp(){
    DismissKeyboardOnTapOutside { EventoriasNavHost(navHostController = rememberNavController()) }
}