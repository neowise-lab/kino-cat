package com.neowise.kinocat.repository

import com.neowise.kinocat.data.database.FilmsDao
import com.neowise.kinocat.data.model.Film
import com.neowise.kinocat.domain.ILocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

class LocalRepository(private val filmsDao: FilmsDao) : ILocalRepository {

    override fun getAll()  = flow {
        emit(filmsDao.getAll())
    }.flowOn(Dispatchers.IO)

    override fun isExists(id: Long)  = flow {
        emit(filmsDao.isExists(id))
    }.flowOn(Dispatchers.IO)

    override fun insert(film: Film) {
        runBlocking(Dispatchers.IO) {
            filmsDao.insertAll(film)
        }
    }

    override fun remove(id: Long) {
        runBlocking(Dispatchers.IO) {
            filmsDao.remove(id)
        }
    }
}