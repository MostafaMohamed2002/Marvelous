package com.mostafadevo.marvelous.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.DetailesScreen(
    title: String,
    description: String,
    image: String,
    navController: NavHostController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    id: Int,
    EmphasizedEasing: Easing = LinearEasing
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = image,
                contentDescription = description,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .padding(8.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "image-${id}"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(durationMillis = 500, easing = EmphasizedEasing)
                        }

                    )
                    .clip(RoundedCornerShape(20.dp))

            )
            Text(text = title,
                modifier = Modifier.sharedElement(
                    state = rememberSharedContentState(key = "name-${id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _, _ ->
                        tween(durationMillis = 500, easing = EmphasizedEasing)
                    }

                ))
            Text(
                text = description
            )
        }
    }
}