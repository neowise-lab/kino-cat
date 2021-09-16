package com.neowise.kinocat.repository

import com.neowise.kinocat.data.api.KinocatService
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.domain.IKinocatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class KinocatRepository(private val kinocatService: KinocatService) : IKinocatRepository {

    override fun getById(id: Long): Flow<Film> = flow {
        emit(kinocatService.getById(id))
    }.flowOn(Dispatchers.IO)

    override fun getAll() : Flow<List<Film>> = flow {
        emit(kinocatService.getAll())
    }.flowOn(Dispatchers.IO)

    override fun getByGenre(genre: String) : Flow<List<Film>> = flow {
        emit(kinocatService.getByGenre(genre))
    }.flowOn(Dispatchers.IO)

    override fun search(criteria: String) : Flow<List<Film>> = flow {
        emit(kinocatService.search(criteria))
    }.flowOn(Dispatchers.IO)

    override fun sendFilm(film: Film) : Flow<Film> = flow {
        emit(kinocatService.sendFilm(film))
    }.flowOn(Dispatchers.IO)
}