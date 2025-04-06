package com.longlegsdev.rhythm.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.longlegsdev.rhythm.presentation.screen.main.MainScreen
import com.longlegsdev.rhythm.presentation.screen.splash.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen {
                navController.popBackStack()
                navController.navigate(Screen.Main.route)
            }
        }
        composable(route = Screen.Main.route) {
            MainScreen(
                navController
            )
        }
    }
}