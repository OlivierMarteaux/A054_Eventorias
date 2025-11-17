package com.oliviermarteaux.localshared.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val screen: Screen, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem(Screen.Home, Icons.Filled.Event, "Events")
    object Account : BottomNavItem(Screen.Account, Icons.Filled.Person, "Profile")
}