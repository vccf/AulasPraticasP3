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

data class ChatGptResponse(
    val choices: List<Choice>
)

data class Choice(
    val text: String
)

interface ChatGptService {
    @POST("v1/completions")
    fun getCompletion(
        @Header("Authorization") apiKey: String?,
        @Body request: ChatGptRequest?
    ): Call<ChatGptResponse?>?
}


