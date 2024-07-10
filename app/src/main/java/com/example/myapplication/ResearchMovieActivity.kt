package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.ApiClient
import com.example.myapplication.ChatGptRequest
import com.example.myapplication.ChatGptResponse
import com.example.myapplication.OmdbMovie
import com.example.myapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResearchMovieActivity: AppCompatActivity() {

    private lateinit var typeMusicRecEdText: EditText
    private lateinit var researchMovieButton: Button
    private lateinit var MovieRecTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.researchmovieactivity)

        typeMusicRecEdText = findViewById(R.id.typeMusicRec)
        researchMovieButton = findViewById(R.id.researchMovie)
        MovieRecTextView= findViewById(R.id.MovieRec)

        researchMovieButton.setOnClickListener {
            val query = typeMusicRecEdText.text.toString().trim()

            // Example: Request recommendations from ChatGPT
            requestRecommendations(query)
        }
    }

    private fun requestRecommendations(query: String) {
        val chatGptRequest = ChatGptRequest(query)
        val apiKey = "sk-proj-lVPKfbY3b2u7jdaUJEofT3BlbkFJg9fkQ8k1LLfDXDKtbCY7"

        val callChatGpt = ApiClient.chatGptService.getCompletion("Bearer $apiKey", chatGptRequest)
        callChatGpt.enqueue(object : Callback<ChatGptResponse> {
            override fun onResponse(call: Call<ChatGptResponse>, response: Response<ChatGptResponse>) {
                if (response.isSuccessful) {
                    val chatGptResponse = response.body()
                    chatGptResponse?.let { recommendations ->
                        if (recommendations.choices.isNotEmpty()) {
                            val movieTitles = recommendations.choices.take(3).map { it.text.trim() }
                            searchMovies(movieTitles)
                        } else {
                            // Handle case where no recommendations are returned
                            displayMessage("No movie recommendations found.")
                        }
                    }
                } else {
                    // Handle unsuccessful response from ChatGPT
                    displayMessage("Failed to get movie recommendations.")
                }
            }

            override fun onFailure(call: Call<ChatGptResponse>, t: Throwable) {
                // Handle failure to communicate with ChatGPT API
                displayMessage("Failed to communicate with ChatGPT API.")
            }
        })
    }

    private fun searchMovies(movieTitles: List<String>) {
        val apiKey = "13365a7" // Replace with your actual OMDb API key

        for (title in movieTitles) {
            val callOMDb = ApiClient.omdbService.searchMoviesByTitle(apiKey, title)
            callOMDb.enqueue(object : Callback<OmdbMovie> {
                override fun onResponse(call: Call<OmdbMovie>, response: Response<OmdbMovie>) {
                    if (response.isSuccessful) {
                        val omdbMovie = response.body()
                        omdbMovie?.let {
                            val movieInfo = "Title: ${it.title}\nYear: ${it.year}\nPlot: ${it.plot}"
                            appendResult(movieInfo)
                        }
                    } else {
                        // Handle unsuccessful response from OMDb API
                        appendResult("Failed to get movie details for $title.")
                    }
                }

                override fun onFailure(call: Call<OmdbMovie>, t: Throwable) {
                    // Handle failure to communicate with OMDb API
                    appendResult("Failed to communicate with OMDb API for $title.")
                }
            })
        }
    }

    private fun appendResult(message: String) {
        val currentText = MovieRecTextView.text.toString().trim()
        val newText = if (currentText.isEmpty()) message else "$currentText\n\n$message"
        MovieRecTextView.text = newText
    }

    private fun displayMessage(message: String) {
        MovieRecTextView.text = message
    }
}

fun <T> Call<T>?.enqueue(callback: Callback<OMDbResponse>) {

}

fun <T> Call<T>?.enqueue(callback: Callback<ChatGptResponse>) {

}

//Explanation:
//MainActivity: Contains UI components (EditText, Button, TextView) for user input and displaying results.
//requestRecommendations: Initiates a request to ChatGPT API to get movie recommendations based on user input.
//searchMovies: Uses OMDb API to search for movie details based on recommended movie titles.
//ApiClient: Manages Retrofit instances and provides access to chatGptService and omdbService.
//Callbacks: Handles asynchronous responses (onResponse for successful responses and onFailure for errors) from both APIs.
//Notes:
//Replace API Keys: Replace "YOUR_CHATGPT_API_KEY" and "YOUR_OMDB_API_KEY" with your actual API keys obtained from respective providers (OpenAI for ChatGPT and OMDb API).
//UI Considerations: Enhance UI as per your app's design requirements (e.g., adding loading indicators, error handling dialogs).
//Error Handling: Implement robust error handling to manage failures in API calls or invalid responses.
