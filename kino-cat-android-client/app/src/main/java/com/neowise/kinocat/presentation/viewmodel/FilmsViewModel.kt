package com.neowise.kinocat.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.neowise.kinocat.domain.Genres
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.presentation.state.FilmUIState
import com.neowise.kinocat.utility.InjectUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class FilmsViewModel(application: Application) : AndroidViewModel(application) {

    private val _filmsList = MutableLiveData<FilmUIState>(FilmUIState.Loading)
    val filmsList: LiveData<FilmUIState> = _filmsList

    private val kinocatRepository = InjectUtils.kinocatRepository
    private val localRepository = InjectUtils.localRepo(application.applicationContext)

    fun searchFilm(criteria: String) {
        if (criteria.isEmpty()) {
            _filmsList.postValue(FilmUIState.Success(emptyList()))
        }
        else {
            viewModelScope.launch {
                kinocatRepository.search(criteria).collectFlow()
            }
        }
    }

    fun fetchFilms(genre: Genres) {
        viewModelScope.launch {
            when(genre) {
                Genres.All -> kinocatRepository.getAll()
                else -> kinocatRepository.getByGenre(genre.code)
            }.collectFlow()
        }
    }

    fun favoriteFilms() {
        viewModelScope.launch {
            localRepository.getAll().collectFlow()
        }
    }

    private suspend fun Flow<List<Film>>.collectFlow() {
        this
            .onStart { _filmsList.value = FilmUIState.Loading }
            .catch { _filmsList.value = FilmUIState.Error(it) }
            .collect { _filmsList.value = FilmUIState.Success(it) }
    }
}