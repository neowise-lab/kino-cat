package com.neowise.kinocat.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.presentation.state.AddUIState
import com.neowise.kinocat.utility.InjectUtils
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddFilmViewModel : ViewModel() {

    private val _uiState = MutableLiveData<AddUIState>()

    val uiState: LiveData<AddUIState> = _uiState

    private val firebase = FirebaseStorage.getInstance()
    private val storageReference = firebase.reference

    private var posterUri: Uri? = null

    fun initPoster(uri: Uri) {
        posterUri = uri
    }

    fun sendFilm(film: Film) {
        if (posterUri == null) {
            _uiState.value = AddUIState.Error(PosterNotExistsError())
        }
        else {
            viewModelScope.launch {
                // send film to server
                InjectUtils.kinocatRepository.sendFilm(film)
                    .onStart { _uiState.value = AddUIState.Loading }
                    .catch { _uiState.value = AddUIState.Error(it) }
                    // if file successfuly send, upload poster image to firebase with model id
                    .collect { inputFile ->
                        val ref = storageReference.child("posters/${inputFile.id}")
                        ref.putFile(posterUri!!)
                            .addOnFailureListener { _uiState.value = AddUIState.Error(it) }
                            .addOnSuccessListener { _uiState.value = AddUIState.Success(inputFile) }
                    }
            }
        }
    }

}