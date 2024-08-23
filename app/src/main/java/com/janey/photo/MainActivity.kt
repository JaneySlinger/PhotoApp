package com.janey.photo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.janey.photo.ui.detailscreen.DetailScreen
import com.janey.photo.ui.homescreen.HomeScreen
import com.janey.photo.ui.theme.PhotoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = Screens.HOME.route) {
                        composable(Screens.HOME.route) {
                            HomeScreen(
                                viewModel = hiltViewModel(),
                                onImageClicked = { id -> navController.navigate("detail/$id") },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable(
                            Screens.DETAIL.route,
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        ) {
                            DetailScreen(
                                viewModel = hiltViewModel(),
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class Screens(val route: String) {
    HOME(route = "home"),
    DETAIL(route = "detail/{id}"),
}