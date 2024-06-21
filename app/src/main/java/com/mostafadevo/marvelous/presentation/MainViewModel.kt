package com.mostafadevo.marvelous.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mostafadevo.marvelous.data.local.CharacterEntity
import com.mostafadevo.marvelous.data.remote.dto.CharacterDTO
import com.mostafadevo.marvelous.domain.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel() {
    val characters: StateFlow<List<CharacterEntity>> = repository.getCharacters()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}