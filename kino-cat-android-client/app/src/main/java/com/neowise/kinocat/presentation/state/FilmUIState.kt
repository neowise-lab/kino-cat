package com.neowise.kinocat.presentation.state

import com.neowise.kinocat.data.model.Film

sealed class FilmUIState{
    object Loading : FilmUIState()
    class Success(val value : List<Film>) : FilmUIState()
    class Error(val e: Throwable) : FilmUIState()
}