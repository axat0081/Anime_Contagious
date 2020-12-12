package com.example.animecontagious.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.animecontagious.API.AnimeAPI
import com.example.animecontagious.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(private val api: AnimeAPI) {
    fun getUpcomingAnimeResults(query: String): LiveData<AnimeResponse> {
        val animeResponse = MutableLiveData<AnimeResponse>()
        api.getUpcomingAnimeList(query).enqueue(object : Callback<AnimeResponse> {
            override fun onResponse(call: Call<AnimeResponse>, response: Response<AnimeResponse>) {
                if (response.isSuccessful) {
                    animeResponse.value = response.body()
                } else {
                    val list = listOf<AnimeResponse.Anime>()
                    val errorAnimeResponse = AnimeResponse(list)
                    animeResponse.value = errorAnimeResponse
                }
            }

            override fun onFailure(call: Call<AnimeResponse>, t: Throwable) {
                val list = listOf<AnimeResponse.Anime>()
                val errorAnimeResponse = AnimeResponse(list)
                animeResponse.value = errorAnimeResponse
            }

        })
        return animeResponse
    }

    fun getAiringAnimeResults(query: String): LiveData<AnimeResponse> {
        val animeResponse = MutableLiveData<AnimeResponse>()
        api.getAiringAnimeList(query).enqueue(object : Callback<AnimeResponse> {

            override fun onResponse(call: Call<AnimeResponse>, response: Response<AnimeResponse>) {
                if (response.isSuccessful) {
                    animeResponse.value = response.body()
                } else {
                    val list = listOf<AnimeResponse.Anime>()
                    val errorAnimeResponse = AnimeResponse(list)
                    animeResponse.value = errorAnimeResponse
                }
            }

            override fun onFailure(call: Call<AnimeResponse>, t: Throwable) {
                val list = listOf<AnimeResponse.Anime>()
                val errorAnimeResponse = AnimeResponse(list)
                animeResponse.value = errorAnimeResponse
            }

        })
        return animeResponse
    }
}