package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimilarFavMovies : AppCompatActivity() {

    private lateinit var etMovieTitle: EditText
    private lateinit var etMovieYear: EditText
    private lateinit var btnSearch: Button
    private lateinit var btnSaveFavorite: Button
    private lateinit var btnViewFavorites: Button
    private lateinit var tvResult: TextView
    private lateinit var ivPoster: ImageView

    private val apiKey = "a8b9348f27837ee10a7f376f98411fdf"
    //private val baseUrl = "https://api.themoviedb.org/3/"
    private lateinit var database: AppDatabase

    //private val tmdbApiService = RetrofitClient.getRetrofitInstance(baseUrl).create(TmdbApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.similarfavmovies)

        etMovieTitle = findViewById(R.id.etMovieTitle)
        etMovieYear = findViewById(R.id.etMovieYear)
        btnSearch = findViewById(R.id.btnSearch)
        btnSaveFavorite = findViewById(R.id.btnSaveFavorite)
        btnViewFavorites = findViewById(R.id.btnViewFavorites)
        tvResult = findViewById(R.id.tvResult)
        ivPoster = findViewById(R.id.ivPoster)

        database = AppDatabase.getDatabase(applicationContext)

        btnSearch.setOnClickListener {
            val title = etMovieTitle.text.toString()
            val year = etMovieYear.text.toString()
            if (title.isNotBlank() && year.isNotBlank()) {
                searchForMovieId(title, year)
            }
        }

        btnSaveFavorite.setOnClickListener {
            val movie = tvResult.tag as? MovieEntity
            movie?.let {
                lifecycleScope.launch {
                    database.movieDao().insert(it)
                    tvResult.text = "Movie saved to favorites!"
                }
            }
        }

        btnViewFavorites.setOnClickListener {
            startActivity(Intent(this, FavouritesActivity::class.java))
        }
    }

    private fun searchForMovieId(title: String, year: String) {
        RetrofitClient.tmdbApiService.searchMovieByNameAndYear(title, year, apiKey).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    val movie = response.body()?.results?.firstOrNull()
                    movie?.let {
                        getRecommendations(it.id)
                        // Load poster image
                        val posterUrl = "https://image.tmdb.org/t/p/w500${it.poster_path}"
                        Glide.with(this@SimilarFavMovies)
                            .load(posterUrl)
                            .into(ivPoster)
                        tvResult.text = "${it.title} (${it.release_date})"
                        tvResult.tag = MovieEntity(it.id, it.title, it.release_date, it.poster_path)
                    } ?: run {
                        tvResult.text = "Movie not found"
                    }
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                tvResult.text = "Failed to load data: ${t.message}"
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
