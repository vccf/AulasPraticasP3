package com.example.myapplication

//import android.telecom.Call
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class OmdbMovie{
    var title: String = ""
    var year: Int= 0
    var plot : String = ""
}
data class OMDbResponse(
    val choices: List<Choice>
)

interface OMDbService {
    @GET("/")
    fun searchMoviesByTitle(
        @Query("apikey") apiKey: String,
        @Query("t") title: String
    ): Call<OmdbMovie>

}


