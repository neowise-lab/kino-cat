package com.neowise.kinocat.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.utility.InjectUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class PreviewViewModel(app: Application) : AndroidViewModel(app) {

    private val favoriteLiveData = MutableLiveData(false)
    val favoriteState: LiveData<Boolean> = favoriteLiveData

    private var film: Film? = null

    private val localRepository = InjectUtils.localRepo(app.applicationContext)

    fun init(film: Film) {
        this.film = film
        viewModelScope.launch {
            localRepository.isExists(film.id)
                .catch { Log.e("ERROR", "error while init film", it) }
                .collect {
                favoriteLiveData.value = it
            }
        }
    }

    fun changeFavorite() {
        Log.e("change favorite", "FAVORITE")
        film?.let {
            if (favoriteLiveData.value!!) {
                viewModelScope.launch {
                    Log.e("change favorite", "UNLIKE")
                    localRepository.remove(film!!.id)
                }
            }
            else {
                viewModelScope.launch(Dispatchers.IO) {
                    Log.e("change favorite", "LIKE")
                    localRepository.insert(film!!)
                }
            }
            favoriteLiveData.value = !favoriteLiveData.value!!
        }
    }
}