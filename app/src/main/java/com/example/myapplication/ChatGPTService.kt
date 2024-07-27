package com.example.myapplication

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

//import android.telecom.Call
//import com.android.volley.Request.Method.POST
data class ChatGptRequest(
    val prompt: String
    //val maxTokens: Int
)

data class ChatGptRequest2(
    val prompt: List<Message>,
    val model: String
    //val maxTokens: Int
)
data class Message(
    val role: String,
    val content: String
)

data class ChatGptResponse(
    val choices: List<Choice>
)

data class Choice(
    val text: String
)

interface ChatGptService {
    @POST("v1/chat/completions")
    fun getCompletion(
        //@Header("Authorization") apiKey: String?,
        @Body request: ChatGptRequest2?
    ): Call<ChatGptResponse?>?
}



