package com.example.myapplication

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class Movie(
    val id: Int,
    val title: String,
    val release_date: String,
    val poster_path: String
)

data class RecommendationsResponse(
    val results: List<Movie>
)

data class SearchResponse(
    val results: List<Movie>
)

interface TmdbApiService {
    @GET("movie/{movie_id}/recommendations")
    fun getRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<RecommendationsResponse>

    @GET("search/movie")
    /*fun searchMovieByTitle(
        @Query("query") title: String,
        @Query("api_key") apiKey: String
    ): Call<SearchResponse>*/

    fun searchMovieByNameAndYear(
        @Query("query") title: String,
        @Query("year") year: String,
        @Query("api_key") apiKey: String
    ): Call<SearchResponse>

}





