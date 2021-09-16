package com.neowise.kinocat.domain

import com.neowise.kinocat.data.model.Film
import kotlinx.coroutines.flow.Flow

interface IKinocatRepository {
    fun getById(id: Long): Flow<Film>
    fun getAll(): Flow<List<Film>>
    fun getByGenre(genre: String): Flow<List<Film>>
    fun search(criteria: String): Flow<List<Film>>
    fun sendFilm(film: Film): Flow<Film>
}