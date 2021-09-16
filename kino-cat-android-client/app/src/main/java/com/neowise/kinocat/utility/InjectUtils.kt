package com.neowise.kinocat.utility

import android.content.Context
import com.neowise.kinocat.data.database.AppDatabase
import com.neowise.kinocat.data.api.RetrofitFactory
import com.neowise.kinocat.domain.IKinocatRepository
import com.neowise.kinocat.domain.ILocalRepository
import com.neowise.kinocat.repository.KinocatRepository
import com.neowise.kinocat.repository.LocalRepository

object InjectUtils {

    private val kinocatService = RetrofitFactory.apiService
    private fun filmsDao(context: Context) = AppDatabase.getDatabase(context).filmsDao()

    val kinocatRepository: IKinocatRepository = KinocatRepository(kinocatService)
    fun localRepo(context: Context): ILocalRepository = LocalRepository(filmsDao(context))
}