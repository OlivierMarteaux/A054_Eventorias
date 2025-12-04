package com.oliviermarteaux.localshared.composables

import android.R.attr.label
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.oliviermarteaux.a054_eventorias.R
import com.oliviermarteaux.localshared.composables.extensions.cdButtonSemantics
import com.oliviermarteaux.localshared.ui.navigation.BottomNavItem
import com.oliviermarteaux.localshared.ui.navigation.Screen
import com.oliviermarteaux.shared.composables.IconSource


@Composable
fun SharedBottomAppBar(
    navController: NavController,
    items: List<BottomNavItem> = listOf(
        BottomNavItem.Home,
        BottomNavItem.Account,
    )
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            val itemTitle = stringResource(item.titleRes)
            val currentItem = if(currentRoute == item.screen.route) stringResource(R.string.you_are_on_this_screen) else ""
            val routeItem = if (currentRoute == item.screen.route) "" else stringResource(
                R.string.double_tap_to_navigate_to_screen,
                itemTitle
            )
            val cdItem = itemTitle + routeItem + currentItem
            NavigationBarItem(
                icon = { SharedIcon(IconSource.VectorIcon(item.icon)) },
                modifier = Modifier.cdButtonSemantics(cdItem),
                label = { Text(itemTitle) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
