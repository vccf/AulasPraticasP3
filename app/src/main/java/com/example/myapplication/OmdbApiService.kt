package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//just similar movies

data class MovieResponse(
    val Title: String,
    val Year: String,
    val Genre: String,
    val Director: String,
    val Plot: String,
    val Poster: String,
    val Response: String
)

data class SearchResponseOmdb(
    val Search: List<MovieResponse>?,
    val totalResults: String?,
    val Response: String
)

interface OmdbApiService {
    @GET("/")
    fun getMovieDetails(@Query("t") title: String, @Query("apikey") apiKey: String): Call<MovieResponse>

    @GET("/")
    //fun searchMovies(@Query("s") query: String, @Query("apikey") apiKey: String, @Query("page") page: Int): Call<SearchResponse>
    fun searchMovies(@Query("s") query: String, @Query("apikey") apiKey: String): Call<SearchResponseOmdb>
}
