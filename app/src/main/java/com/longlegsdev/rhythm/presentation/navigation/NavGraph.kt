package com.longlegsdev.rhythm.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.longlegsdev.rhythm.presentation.screen.main.MainScreen
import com.longlegsdev.rhythm.presentation.screen.splash.SplashScreen
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.service.player.PlaybackState

@Composable
fun NavGraph(
    navController: NavHostController,
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val playbackState by playerViewModel.playbackState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = when (playbackState) {
            PlaybackState.IDLE -> Screen.Splash.route
            else -> Screen.Main.route
        }
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