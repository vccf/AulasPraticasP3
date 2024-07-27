package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimilarMovies2 : AppCompatActivity() {

    private lateinit var etMovieTitle: EditText
    private lateinit var etMovieYear: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvResult: TextView
    //private lateinit var ivPoster: ImageView

    private val apiKey = "a8b9348f27837ee10a7f376f98411fdf"
    //private val tmdbApiService = retrofit(baseUrl).create(TmdbApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.similar_movies)

        etMovieTitle = findViewById(R.id.etMovieTitle)
        etMovieYear = findViewById(R.id.etMovieYear)
        btnSearch = findViewById(R.id.btnSearch)
        tvResult = findViewById(R.id.tvResult)
        //ivPoster = findViewById(R.id.ivPoster)

        btnSearch.setOnClickListener {
            val title = etMovieTitle.text.toString()
            val year = etMovieYear.text.toString()
            if (title.isNotBlank() && year.isNotBlank()) {
                searchForMovieId(title, year)
            }
        }
    }

    private fun searchForMovieId(title: String, year: String) {
        RetrofitClient.tmdbApiService.searchMovieByNameAndYear(title, year, apiKey).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    val movie = response.body()?.results?.firstOrNull()
                    movie?.let {
                        getRecommendations2(it.id)
                        //val posterUrl = "https://image.tmdb.org/t/p/w500${it.poster_path}"
                        //Picasso.get().load(posterUrl).into(ivPoster)
                    } ?: run {
                        tvResult.text = "Movie not found"
                    }
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }

    private fun getRecommendations(movieId: Int) {
        RetrofitClient.tmdbApiService.getRecommendations(movieId, apiKey).enqueue(object : Callback<RecommendationsResponse> {
            override fun onResponse(call: Call<RecommendationsResponse>, response: Response<RecommendationsResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results?.take(10) ?: emptyList()
                    val resultText = movies.joinToString("\n") { movie ->
                        "${movie.title} (${movie.release_date})"
                    }
                    tvResult.text = resultText
                    // Optionally load poster images
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<RecommendationsResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }

    private fun getRecommendations2(movieId: Int) {
        RetrofitClient.tmdbApiService
            .getRecommendations(movieId, apiKey)
            .enqueue(object : Callback<RecommendationsResponse> {
                override fun onResponse(call: Call<RecommendationsResponse>, response: Response<RecommendationsResponse>) {
                    if (response.isSuccessful) {
                        val movies = response.body()?.results?.take(10) ?: emptyList()
                        val resultText = movies.joinToString("\n") { movie ->
                            "${movie.title} (${movie.release_date})"
                        }
                        tvResult.text = resultText
                    } else {
                        tvResult.text = "Error: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<RecommendationsResponse>, t: Throwable) {
                    tvResult.text = "Failed to load data: ${t.message}"
                }
            })
    }
}
