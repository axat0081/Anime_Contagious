package com.example.animecontagious.API

import androidx.lifecycle.LiveData
import com.example.animecontagious.data.AnimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeAPI {
    companion object {
        const val BASE_URL = "https://api.jikan.moe/v3/top/"
    }

    @GET("anime/{id}/upcoming")
    fun getAnimeList(@Path("id") id: String): Call<AnimeResponse>
}