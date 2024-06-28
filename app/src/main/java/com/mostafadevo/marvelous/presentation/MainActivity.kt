package com.mostafadevo.marvelous.presentation

import android.os.Bundle
import android.view.animation.PathInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.mostafadevo.marvelous.data.local.CharacterEntity
import com.mostafadevo.marvelous.presentation.screens.DetailesScreen
import com.mostafadevo.marvelous.presentation.screens.HomeScreen
import com.mostafadevo.marvelous.presentation.screens.Screens
import com.mostafadevo.marvelous.presentation.theme.MarvelousTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MarvelousTheme {
                MaverlousApp(navController)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MaverlousApp(navController: NavHostController) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Screens.HOME_SCREEN) {
            composable(Screens.HOME_SCREEN) {
                HomeScreen(
                    navController = navController,
                    animatedVisibilityScope = this@composable,
                )
            }
            composable("${Screens.DETAIL_SCREEN}/{characterJson}") { backStackEntry ->
                val characterJson = backStackEntry.arguments?.getString("characterJson")
                characterJson.let {
                    val character = Gson().fromJson(it, CharacterEntity::class.java)
                    DetailesScreen(
                        id = character.id,
                        title = character.name,
                        description = character.description,
                        image = character.thumbnail,
                        navController = navController,
                        animatedVisibilityScope = this@composable,
                    )
                }
            }
        }
    }
}