package com.example.myapplication
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimilarMovies : AppCompatActivity() {

    private lateinit var etMovieTitle: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvResult: TextView
    private lateinit var btnLoadMore: Button

    private val apiKey = "13365a7"

    private var currentPage = 1
    private var totalResults = 0
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.similar_movies)

        etMovieTitle = findViewById(R.id.etMovieTitle)
        btnSearch = findViewById(R.id.btnSearch)
        tvResult = findViewById(R.id.tvResult)
        btnLoadMore = findViewById(R.id.btnLoadMore)

        btnSearch.setOnClickListener {
            val title = etMovieTitle.text.toString()
            if (title.isNotBlank()) {
                /*currentPage = 1
                searchForSimilarMovies(title, currentPage)*/
                //fetchMovieDetails(title)
            }
        }
    }
}
        /*btnLoadMore.setOnClickListener {
            if (!isLoading && currentPage * 10 < totalResults) {
                currentPage++
                searchForSimilarMovies(etMovieTitle.text.toString(), currentPage)
            }
        }
    }

    private fun fetchMovieDetails(title: String) {
        RetrofitClient.apiService.getMovieDetails(title, apiKey).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movie = response.body()
                    if (movie != null && movie.Response == "True") {
                        val keywords = extractKeywords(movie.Genre, movie.Plot)
                        searchForSimilarMovies(keywords)
                    } else {
                        tvResult.text = "Movie not found"
                    }
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }

    private fun extractKeywords(genre: String, plot: String): String {
        // Extract keywords from genres and plot
        val genreKeywords = genre.split(",").map { it.trim() }.joinToString(" ")
        val plotKeywords = plot.split(" ").take(40).joinToString(" ") // Take first 40 words from plot
        return "$genreKeywords $plotKeywords"
    }

    private fun searchForSimilarMovies(keywords: String) {
        RetrofitClient.apiService.searchMovies(keywords, apiKey).enqueue(object : Callback<SearchResponseOmdb> {
            override fun onResponse(call: Call<SearchResponseOmdb>, response: Response<SearchResponseOmdb>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.Search?.take(10) ?: emptyList() // Limit to top 10 movies
                    val resultText = movies.joinToString("\n") { movie ->
                        "${movie.Title} (${movie.Year})"
                    }
                    tvResult.text = resultText
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }

    private fun fetchMovieDetails(title: String) {
        RetrofitClient.apiService.getMovieDetails(title, apiKey).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movie = response.body()
                    if (movie != null && movie.Response == "True") {
                        val genre = movie.Genre
                        searchForSimilarMovies(genre)
                    } else {
                        tvResult.text = "Movie not found"
                    }
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }

    private fun searchForSimilarMovies(genre: String) {
        //isLoading=true
        RetrofitClient.apiService.searchMovies(genre, apiKey).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                //isLoading=false
                if (response.isSuccessful) {
                    val movies = response.body()?.Search ?: emptyList()
                    val resultText = movies.joinToString("\n") { movie ->
                        "${movie.Title} (${movie.Year})"
                    }
                    tvResult.text = resultText
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }

    private fun searchForSimilarMovies(title: String, page: Int) {
        isLoading = true
        RetrofitClient.apiService.searchMovies(title, apiKey, page).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                isLoading = false
                if (response.isSuccessful) {
                    /*val movies = response.body()?.Search ?: emptyList()
                    val resultText = movies.joinToString("\n") { movie ->
                        "${movie.Title} (${movie.Year})"
                    }*/
                    val searchResponse = response.body()
                    val movies = searchResponse?.Search ?: emptyList()
                    totalResults = searchResponse?.totalResults?.toIntOrNull() ?: 0

                    val resultText = movies.joinToString("\n") { movie ->
                        "${movie.Title} (${movie.Year})"
                    }

                    tvResult.text = resultText
                    // Show or hide "Load More" button based on available pages
                    btnLoadMore.visibility = if (currentPage * 10 < totalResults) View.VISIBLE else View.GONE

                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                isLoading = false
                tvResult.text = "Failed to load data"
            }
        })
    }
}

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etMovieTitle: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvResult: TextView

    private val apiKey = "YOUR_OMDB_API_KEY" // Replace with your OMDB API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etMovieTitle = findViewById(R.id.etMovieTitle)
        btnSearch = findViewById(R.id.btnSearch)
        tvResult = findViewById(R.id.tvResult)

        btnSearch.setOnClickListener {
            val title = etMovieTitle.text.toString()
            if (title.isNotBlank()) {
                fetchMovieDetails(title)
            }
        }
    }

    private fun fetchMovieDetails(title: String) {
        RetrofitClient.apiService.getMovieDetails(title, apiKey).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movie = response.body()
                    if (movie != null && movie.Response == "True") {
                        val keywords = extractKeywords(movie.Genre, movie.Plot)
                        searchForSimilarMovies(keywords)
                    } else {
                        tvResult.text = "Movie not found"
                    }
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }

    private fun extractKeywords(genre: String, plot: String): String {
        // Extract keywords from genres and plot
        val genreKeywords = genre.split(",").map { it.trim() }.joinToString(" ")
        val plotKeywords = plot.split(" ").take(10).joinToString(" ") // Take first 10 words from plot
        return "$genreKeywords $plotKeywords"
    }

    private fun searchForSimilarMovies(keywords: String) {
        RetrofitClient.apiService.searchMovies(keywords, apiKey).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.Search?.take(10) ?: emptyList() // Limit to top 10 movies
                    val resultText = movies.joinToString("\n") { movie ->
                        "${movie.Title} (${movie.Year})"
                    }
                    tvResult.text = resultText
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }
}

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var etMovieTitle: EditText
    private lateinit var btnSearch: Button
    private lateinit var tvResult: TextView

    private val apiKey = "YOUR_OMDB_API_KEY" // Replace with your OMDB API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etMovieTitle = findViewById(R.id.etMovieTitle)
        btnSearch = findViewById(R.id.btnSearch)
        tvResult = findViewById(R.id.tvResult)

        btnSearch.setOnClickListener {
            val title = etMovieTitle.text.toString()
            if (title.isNotBlank()) {
                fetchMovieDetails(title)
            }
        }
    }

    private fun fetchMovieDetails(title: String) {
        RetrofitClient.apiService.getMovieDetails(title, apiKey).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movie = response.body()
                    if (movie != null && movie.Response == "True") {
                        val genre = movie.Genre
                        searchForSimilarMovies(genre)
                    } else {
                        tvResult.text = "Movie not found"
                    }
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }

    private fun searchForSimilarMovies(genre: String) {
        RetrofitClient.apiService.searchMovies(genre, apiKey).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.Search ?: emptyList()
                    val resultText = movies.joinToString("\n") { movie ->
                        "${movie.Title} (${movie.Year})"
                    }
                    tvResult.text = resultText
                } else {
                    tvResult.text = "Error: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                tvResult.text = "Failed to load data"
            }
        })
    }
}
*/


