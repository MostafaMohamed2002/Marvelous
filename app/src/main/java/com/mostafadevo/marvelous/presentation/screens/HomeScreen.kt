package com.mostafadevo.marvelous.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.mostafadevo.marvelous.data.local.CharacterEntity
import com.mostafadevo.marvelous.presentation.MainViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    navController: NavHostController,
    mViewModel: MainViewModel = hiltViewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    EmphasizedEasing: Easing = LinearEasing
) {
    val characters by mViewModel.characters.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Marvelous Home", modifier = Modifier.padding(start = 16.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home"
                        , modifier = Modifier.padding(start = 16.dp)
                    )
                },

            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(characters) { character ->
                ElevatedCard(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                val moshi = Moshi
                                    .Builder()
                                    .add(KotlinJsonAdapterFactory())
                                    .build()
                                val jsonAdapter = moshi.adapter(CharacterEntity::class.java)
                                val characterJson = URLEncoder
                                    .encode(
                                        jsonAdapter.toJson(character),
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    .replace("+", "%20")
                                navController.navigate("${Screens.DETAIL_SCREEN}/$characterJson")

                            }
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = character.thumbnail,
                            contentDescription = character.description,
                            modifier = Modifier
                                .sharedElement(
                                    state = rememberSharedContentState(key = "image-${character.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 500, easing = EmphasizedEasing)
                                    }
                                )
                                .size(70.dp)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = character.name,
                                modifier = Modifier.sharedElement(
                                    state = rememberSharedContentState(key = "name-${character.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    boundsTransform = { _, _ ->
                                        tween(durationMillis = 500, easing = EmphasizedEasing)
                                    }

                                ))
                        }
                    }
                }
            }
        }
    }
}