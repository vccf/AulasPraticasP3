package com.example.myapplication

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL_CHATGPT = "https://api.openai.com/"
    private const val BASE_URL_OMDB = "http://www.omdbapi.com/"

    private val gson = GsonBuilder().setLenient().create()

    private val retrofitChatGpt: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_CHATGPT)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val retrofitOMDb: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_OMDB)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val chatGptService: ChatGptService = retrofitChatGpt.create(ChatGptService::class.java)
    val omdbService: OMDbService = retrofitOMDb.create(OMDbService::class.java)
}

//https://platform.openai.com/docs/overview
//https://www.omdbapi.com/

