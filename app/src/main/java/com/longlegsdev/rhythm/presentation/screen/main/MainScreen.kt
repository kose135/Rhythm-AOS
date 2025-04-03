package com.longlegsdev.rhythm.presentation.screen.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun MainScreen(
    navController: NavHostController,
//    viewModel: MainViewModel = hiltViewModel(),
    activity: Activity? = LocalContext.current as? Activity
) {

    /* back key event */
//    BackHandler {
//        if (deleteEnable) {
//            deleteEnable = false
//            selectedNotes.clear()
//        } else {
//            activity?.finish()
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Welcome to Home!")

    }
}