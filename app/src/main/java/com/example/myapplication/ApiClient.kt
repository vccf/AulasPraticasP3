package com.example.myapplication

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL_CHATGPT = "https://api.openai.com/"
    private const val BASE_URL_OMDB = "http://www.omdbapi.com/"

    private val gson = GsonBuilder().setLenient().create()

//    private val retrofitChatGpt: Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL_CHATGPT)
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()

    private val retrofitOMDb: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_OMDB)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

//    val chatGptService: ChatGptService = retrofitChatGpt.create(ChatGptService::class.java)
    val omdbService: OMDbService = retrofitOMDb.create(OMDbService::class.java)

    //val apiKey = "sk-proj-lVPKfbY3b2u7jdaUJEofT3BlbkFJg9fkQ8k1LLfDXDKtbCY7"
    //val apiKey= "sk-proj-ZGe1SyP92LEvZjZVnUEmT3BlbkFJNRSZzLyUXFYDQyNQczYs"
    //val apiKey = "sk-proj-UNPYgtk3dAVr8BNJ1nzRT3BlbkFJzAQNJ6geAEqEK4vM12ai"
    //val apiKey = "sk-None-6b6Bw0mj86jrovPHlo48T3BlbkFJ8kwb0sqhynVMS1Zj75NJ"
    //val apiKey = "sk-None-FRhSobUxBtEdlTG7VHH9T3BlbkFJstQ277tqgE7UkK4rjqef"
    val apiKey = "sk-proj-Sy3yT14nsFtEoz1y4mBPT3BlbkFJrhM813tfOwUw3rLBvNts"

    private val retrofitChatGpt: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CHATGPT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $apiKey")
                            .build()
                        chain.proceed(request)
                    }
                    .build()
            )
            .build()
    }

    val chatGptService: ChatGptService by lazy {
        retrofitChatGpt.create(ChatGptService::class.java)
    }

}

//val retrofit = Retrofit.Builder()
//    .baseUrl("https://api.openai.com/")
//    .addConverterFactory(GsonConverterFactory.create())
//    .client(OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val request = chain.request().newBuilder()
//                .addHeader("Authorization", "Bearer YOUR_API_KEY")
//                .build()
//            chain.proceed(request)
//        }
//        .build())
//    .build()

//https://platform.openai.com/docs/overview
//https://www.omdbapi.com/

