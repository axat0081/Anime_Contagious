package com.example.animecontagious.api

import com.example.animecontagious.data.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeAPI {
    companion object {
        const val BASE_URL = "https://api.jikan.moe/v3/top/"
    }

    @GET("anime/{id}/upcoming")
    suspend fun getUpcomingAnimeList(@Path("id") id: String): AnimeResponse

    @GET("anime/{id}/airing")
    suspend fun getAiringAnimeList(@Path("id") id: String): AnimeResponse
}