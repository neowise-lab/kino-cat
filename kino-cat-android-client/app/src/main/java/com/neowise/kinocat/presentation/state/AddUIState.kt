package com.neowise.kinocat.presentation.state

import com.neowise.kinocat.data.model.Film

sealed class AddUIState{
    object Loading : AddUIState()
    class Success(film: Film) : AddUIState()
    class Error(val error: Throwable) : AddUIState()
    object PosterNotExists : AddUIState()
}