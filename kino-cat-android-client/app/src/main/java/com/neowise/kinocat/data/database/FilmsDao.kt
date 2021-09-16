package com.neowise.kinocat.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.neowise.kinocat.data.model.Film

@Dao
interface FilmsDao {

    @Query("select * from film")
    suspend fun getAll(): List<Film>

    @Insert
    suspend fun insertAll(vararg films: Film)

    @Query("select exists(select * from film where id = :id)")
    suspend fun isExists(id: Long): Boolean

    @Query("delete from film where id = :id")
    suspend fun remove(id: Long)
}