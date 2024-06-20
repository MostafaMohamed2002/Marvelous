package com.mostafadevo.marvelous.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.mostafadevo.marvelous.BuildConfig
import com.mostafadevo.marvelous.presentation.theme.MarvelousTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarvelousTheme {
                MarvelousApp()
            }
        }
    }
}

@Composable
fun MarvelousApp() {
}
