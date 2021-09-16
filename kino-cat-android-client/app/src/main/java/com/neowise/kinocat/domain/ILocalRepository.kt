package com.neowise.kinocat.domain

import com.neowise.kinocat.data.model.Film
import kotlinx.coroutines.flow.Flow

interface ILocalRepository {
    fun getAll(): Flow<List<Film>>
    fun isExists(id: Long): Flow<Boolean>
    fun insert(film: Film)
    fun remove(id: Long)
}