package com.mostafadevo.marvelous.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import com.mostafadevo.marvelous.BuildConfig
import com.mostafadevo.marvelous.R
import com.mostafadevo.marvelous.data.local.CharacterEntity
import com.mostafadevo.marvelous.data.remote.MarvelApi
import com.mostafadevo.marvelous.data.remote.dto.CharacterDTO
import com.mostafadevo.marvelous.presentation.theme.MarvelousTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            MarvelousTheme {
                val mMainViewModel: MainViewModel = hiltViewModel()
                CharacterListScreen(viewmodel = mMainViewModel)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(viewmodel: MainViewModel) {
    val characters by viewmodel.characters.collectAsStateWithLifecycle(emptyList())
    val scrollState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Marvelous")
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Close",
                        modifier = Modifier.padding(8.dp)
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    )
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(innerPadding)
        ) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                items( characters) { character ->
                    CharacterItem(character = character)
                }
            }
        }
    }
}
@Composable
fun CharacterItem(character: CharacterEntity) {
    OutlinedCard(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(CircleShape)
                    .weight(1f),
                model = character.thumbnail,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                onError = {
                    Log.e("CharacterItem", "Error loading image: ${it.result.throwable}")
                },
                onLoading = {
                    Log.d("CharacterItem", "Loading image...")
                },
                onSuccess = {
                    Log.d("CharacterItem", "Image loaded successfully")
                }
            )
            Text(
                text = character.name,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            )


        }
    }
}
