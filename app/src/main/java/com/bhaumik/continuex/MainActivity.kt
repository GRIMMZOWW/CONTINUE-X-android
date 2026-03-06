package com.bhaumik.continuex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bhaumik.continuex.ui.screens.*
import com.bhaumik.continuex.ui.theme.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContinueXTheme {
                ContinueXApp()
            }
        }
    }
}

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object HowItWorks : Screen("how_it_works", "How it Works", Icons.Default.Info)
    object FAQ : Screen("faq", "FAQ", Icons.Default.QuestionAnswer)
    object About : Screen("about", "About", Icons.Default.Person)
}

@Composable
fun ContinueXApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        Screen.Home,
        Screen.HowItWorks,
        Screen.FAQ,
        Screen.About
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = BackgroundDark,
                modifier = Modifier.width(300.dp)
            ) {
                Spacer(Modifier.height(48.dp))
                Text(
                    "CONTINUE-X",
                    modifier = Modifier.padding(24.dp),
                    color = AccentIndigo,
                    style = MaterialTheme.typography.headlineMedium
                )
                items.forEach { screen ->
                    NavigationDrawerItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        // Simple navigation for stability during hard fix
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = AccentIndigo.copy(alpha = 0.2f),
                            selectedIconColor = AccentIndigo,
                            selectedTextColor = Color.White,
                            unselectedContainerColor = Color.Transparent,
                            unselectedIconColor = TextGray,
                            unselectedTextColor = TextGray
                        )
                    )
                }
            }
        },
        content = {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundDark),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                    ) + fadeOut(animationSpec = tween(300))
                },
                popEnterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                    ) + fadeIn(animationSpec = tween(300))
                },
                popExitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                composable(Screen.Home.route) { 
                    HomeScreen(onOpenDrawer = { scope.launch { drawerState.open() } }) 
                }
                composable(Screen.HowItWorks.route) { 
                    HowItWorksScreen(onOpenDrawer = { scope.launch { drawerState.open() } }) 
                }
                composable(Screen.FAQ.route) { 
                    FAQScreen(onOpenDrawer = { scope.launch { drawerState.open() } }) 
                }
                composable(Screen.About.route) { 
                    AboutScreen(onOpenDrawer = { scope.launch { drawerState.open() } }) 
                }
            }
        }
    )
}