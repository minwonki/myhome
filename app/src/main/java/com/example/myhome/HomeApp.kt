package com.example.myhome

import android.graphics.drawable.VectorDrawable
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myhome.ui.graph.MainScreen
import com.example.myhome.ui.graph.MyHomeNavGraph
import java.util.*

@Composable
fun HomeApp() {
    val tabs = remember { MainMenuTabs.values() }
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { MainBottomBar(navController = navController, tabs = tabs) }
    ) {
        MyHomeNavGraph(
            navController = navController,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun MainBottomBar(navController: NavController, tabs: Array<MainMenuTabs>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: MainMenuTabs.HOME.route

    val routes = remember { MainMenuTabs.values().map { it.route } }
    if (currentRoute in routes) {
        BottomNavigation {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = { Icon(tab.icon, contentDescription = stringResource(id = tab.title)) },
                    label = { Text(stringResource(tab.title).uppercase(Locale.getDefault())) },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = LocalContentColor.current,
                )
            }
        }
    }
}


enum class MainMenuTabs(
    @StringRes val title: Int,
    val icon : ImageVector,
    val route: String
) {
    HOME(R.string.main_home, Icons.Rounded.Home, MainScreen.MainHome.route),
    CARD(R.string.main_card, Icons.Rounded.Favorite, MainScreen.MainCard.route)
}

