package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class ResearchMovie {
//
//    //lateinit var : chatGptRequest
//
//    // Example usage
//    //openAI key sk-proj-lVPKfbY3b2u7jdaUJEofT3BlbkFJg9fkQ8k1LLfDXDKtbCY7
//    //val callChatGpt = ApiClient.chatGptService.getCompletion("Bearer $YOUR_CHATGPT_API_KEY", chatGptRequest)
//    val callChatGpt = ApiClient.chatGptService.getCompletion("sk-proj-lVPKfbY3b2u7jdaUJEofT3BlbkFJg9fkQ8k1LLfDXDKtbCY7", chatGptRequest)
//    callChatGpt.enqueue(object : Callback<ChatGptResponse> {
//        override fun onResponse(call: Call<ChatGptResponse>, response: Response<ChatGptResponse>) {
//            if (response.isSuccessful) {
//                val chatGptResponse = response.body()
//                // Handle successful response
//            } else {
//                // Handle unsuccessful response
//            }
//        }
//
//        override fun onFailure(call: Call<ChatGptResponse>, t: Throwable) {
//            // Handle failure
//        }
//    })
//
//    val callOMDb = ApiClient.omdbService.getMovieDetails("13365a7", "Inception")
//    callOMDb.enqueue(object : Callback<OmdbMovie> {
//        override fun onResponse(call: Call<OmdbMovie>, response: Response<OmdbMovie>) {
//            if (response.isSuccessful) {
//                val omdbMovie = response.body()
//                // Handle successful response
//            } else {
//                // Handle unsuccessful response
//            }
//        }
//
//        override fun onFailure(call: Call<OmdbMovie>, t: Throwable) {
//            // Handle failure
//        }
//    })
//
//}

//class ResearchMovie {
//
//    // Example usage
//    private val apiClient = ApiClient() // Assuming ApiClient is properly defined
//
//    fun requestChatGpt(prompt: String) {
//        val chatGptRequest = ChatGptRequest(prompt = prompt, maxTokens = 150)
//
//        // Replace YOUR_API_KEY with your actual API key or directly use it as shown
//        val callChatGpt = apiClient.chatGptService.getCompletion("Bearer sk-proj-lVPKfbY3b2u7jdaUJEofT3BlbkFJg9fkQ8k1LLfDXDKtbCY7", chatGptRequest)
//
//        callChatGpt.enqueue(object : Callback<ChatGptResponse> {
//            override fun onResponse(call: Call<ChatGptResponse>, response: Response<ChatGptResponse>) {
//                if (response.isSuccessful) {
//                    val chatGptResponse = response.body()
//                    // Handle successful response
//                    chatGptResponse?.let {
//                        println("ChatGPT Response: ${it.choices.firstOrNull()?.text ?: "No response"}")
//                        // Process the response as needed
//                    }
//                } else {
//                    // Handle unsuccessful response
//                    println("Error: ${response.code()} - ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ChatGptResponse>, t: Throwable) {
//                // Handle failure
//                println("Failed to execute API request: ${t.message}")
//            }
//        })
//    }
//}

//ChatGPT
//To define ChatGptRequest in your ResearchMovie class, you typically need to create a data class or a plain Kotlin class that represents the structure of the request you want to send to the ChatGPT API. Here’s a basic outline of how you can define ChatGptRequest:
//
//Step 1: Define the ChatGptRequest Data Class
//Assuming your ChatGptRequest needs to include specific parameters that the ChatGPT API expects, you would define it as follows:
//
//kotlin
//Copy code
//In this example:
//
////prompt: Represents the text prompt or query you want to send to the ChatGPT API.
////maxTokens: Represents an example parameter (you would replace this with the actual parameters required by the ChatGPT API).
////Step 2: Integrate ChatGptRequest into your ResearchMovie Class
//Now, integrate this ChatGptRequest into your ResearchMovie class:
//
//    kotlin
//Copy code
//class ResearchMovie {
//
//    // Example usage
//    private val apiClient = ApiClient() // Assuming ApiClient is properly defined
//
//    fun requestChatGpt(prompt: String) {
//        val chatGptRequest = ChatGptRequest(prompt = prompt, maxTokens = 150)
//
//        // Replace YOUR_API_KEY with your actual API key or directly use it as shown
//        val callChatGpt = apiClient.chatGptService.getCompletion("Bearer sk-proj-lVPKfbY3b2u7jdaUJEofT3BlbkFJg9fkQ8k1LLfDXDKtbCY7", chatGptRequest)
//
//        callChatGpt.enqueue(object : Callback<ChatGptResponse> {
//            override fun onResponse(call: Call<ChatGptResponse>, response: Response<ChatGptResponse>) {
//                if (response.isSuccessful) {
//                    val chatGptResponse = response.body()
//                    // Handle successful response
//                    chatGptResponse?.let {
//                        println("ChatGPT Response: ${it.choices.firstOrNull()?.text ?: "No response"}")
//                        // Process the response as needed
//                    }
//                } else {
//                    // Handle unsuccessful response
//                    println("Error: ${response.code()} - ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ChatGptResponse>, t: Throwable) {
//                // Handle failure
//                println("Failed to execute API request: ${t.message}")
//            }
//        })
//    }
//}
//Explanation:
//ChatGptRequest: This data class encapsulates the parameters (prompt, maxTokens, etc.) that you want to send to the ChatGPT API.
//ApiClient: Assuming ApiClient is a class or object that manages API requests and has a chatGptService property or method that allows you to interact with the ChatGPT API.
//requestChatGpt: This method in ResearchMovie class initializes a ChatGptRequest with a prompt and other parameters, sends an asynchronous API request using Retrofit (assuming Retrofit is used based on enqueue method), and handles the API response and potential errors.
//Ensure you have Retrofit set up properly with the chatGptService defined to match the ChatGPT API endpoint (getCompletion in this case), and adjust the ChatGptRequest parameters (prompt, maxTokens, etc.) according to the specific requirements of the ChatGPT API you are integrating with.

//override fun onResponse(call: Call<ChatGptResponse>, response: Response<ChatGptResponse>) {
//    if (response.isSuccessful) {
//        val chatGptResponse = response.body()
//        chatGptResponse?.let {
//            val firstChoiceText = it.choices.firstOrNull()?.text ?: "No choices available"
//            println("First choice text: $firstChoiceText")
//            // Process the response as needed
//        }
//    } else {
//        // Handle unsuccessful response
//        println("Error: ${response.code()} - ${response.message()}")
//    }
//}

//class ResearchMovie {
//
//    private val apiClient = ApiClient // Assuming ApiClient is properly defined
//
//    fun requestChatGpt(prompt: String) {
//        val chatGptRequest = ChatGptRequest(prompt = prompt, maxTokens = 150)
//
//        val callChatGpt = apiClient.chatGptService.getCompletion("Bearer sk-proj-lVPKfbY3b2u7jdaUJEofT3BlbkFJg9fkQ8k1LLfDXDKtbCY7", chatGptRequest)
//
//        callChatGpt.enqueue(object : Callback<ChatGptResponse> {
//            override fun onResponse(call: Call<ChatGptResponse>, response: Response<ChatGptResponse>) {
//                if (response.isSuccessful) {
//                    val chatGptResponse = response.body()
//                    chatGptResponse?.let {
//                        val firstChoiceText = it.choices.firstOrNull()?.text ?: "No choices available"
//                        println("First choice text: $firstChoiceText")
//                        // Process the response as needed
//                    }
//                } else {
//                    // Handle unsuccessful response
//                    println("Error: ${response.code()} - ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ChatGptResponse>, t: Throwable) {
//                // Handle failure
//                println("Failed to execute API request: ${t.message}")
//            }
//        })
//    }
//}

//Notes:
//API Response Structure: Ensure ChatGptResponse and Choice classes match the actual JSON structure returned by the ChatGPT API. Adjust property names and types accordingly.
//Null Safety: Use Kotlin's null safety features (?., let { }) to avoid NullPointerException when accessing response properties.
//Error Handling: Handle both successful and unsuccessful API responses appropriately to provide meaningful feedback or take corrective actions in your application.
//By following these steps and ensuring your response handling aligns with the actual API response structure, you should be able to resolve the "unresolved reference choices" issue and effectively integrate with the ChatGPT API using Retrofit.
//
//Ensure that ChatGptResponse and its properties (choices in this case) are correctly parsed and accessed based on the actual JSON structure of the API response.
//Use safe calls (?.) and null checks (let { }) to safely access properties of chatGptResponse.
//Error Handling:
//Always handle potential errors gracefully, both in case of unsuccessful API responses and in case of network failures (onFailure callback).

class ResearchMovie {

    private val apiClient = ApiClient // Assuming ApiClient is properly defined as an object or class

    fun requestChatGpt(prompt: String) {
        val chatGptRequest = ChatGptRequest(prompt = prompt) //, maxTokens = 150)

        val callChatGpt = apiClient.chatGptService.getCompletion("Bearer YOUR_API_KEY", chatGptRequest)

        callChatGpt.enqueue2(object : Callback<ChatGptResponse> {
            override fun onResponse(call: Call<ChatGptResponse>, response: Response<ChatGptResponse>) {
                if (response.isSuccessful) {
                    val chatGptResponse = response.body()
                    chatGptResponse?.let {
                        val firstChoiceText = it.choices.firstOrNull()?.text ?: "No choices available"
                        println("First choice text: $firstChoiceText")
                        // Process the response as needed
                    }
                } else {
                    // Handle unsuccessful response
                    println("Error: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ChatGptResponse>, t: Throwable) {
                // Handle failure
                println("Failed to execute API request: ${t.message}")
            }
        })
    }

//    val callOMDb = ApiClient.omdbService.searchMoviesByTitle("13365a7", "Inception")
//    callOMDb.enqueue(object : Callback<OMDbResponse> {
////        override fun onResponse(call: Call<OmdbMovie>, response: Response<OmdbMovie>) {
////            if (response.isSuccessful) {
////                val omdbMovie = response.body()
////                // Handle successful response
////            } else {
////                // Handle unsuccessful response
////            }
////        }
////
////        override fun onFailure(call: Call<OmdbMovie>, t: Throwable) {
////            // Handle failure
////        }
//
//        override fun onResponse(call: Call<OMDbResponse>, response: Response<OMDbResponse>) {
//            TODO("Not yet implemented")
//        }
//
//        override fun onFailure(call: Call<OMDbResponse>, t: Throwable) {
//            TODO("Not yet implemented")
//        }
//    })

//    val call = ApiClient.omdbService.searchMoviesByTitle("your_api_key", "movie_title")
//
//    call.enqueue(object : Callback<OMDbResponse> {
//        override fun onResponse(call: Call<OMDbResponse>, response: Response<OMDbResponse>) {
//            if (response.isSuccessful) {
//                val omdbResponse = response.body()
//                // Process the omdbResponse here
//            } else {
//                // Handle error response
//            }
//        }
//
//        override fun onFailure(call: Call<OMDbResponse>, t: Throwable) {
//            // Handle failure
//        }
//    })
}

//https://platform.openai.com/docs/quickstart







