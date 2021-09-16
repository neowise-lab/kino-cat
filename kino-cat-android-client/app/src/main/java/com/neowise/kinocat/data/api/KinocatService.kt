package com.neowise.kinocat.data.api

import com.neowise.kinocat.data.model.Film
import retrofit2.Call
import retrofit2.http.*

interface KinocatService {

    @GET("{id}")
    suspend fun getById(@Path("id") id: Long): Film

    @GET(".")
    suspend fun getAll() : List<Film>

    @POST(".")
    suspend fun sendFilm(@Body film: Film) : Film

    @GET("genre/{genre}")
    suspend fun getByGenre(@Path("genre") genre: String) : List<Film>

    @GET("search")
    suspend fun search(@Query("criteria") criteria: String) : List<Film>

}