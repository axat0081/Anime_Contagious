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
    fun getAnimeResults(query: String): LiveData<AnimeResponse> {
        val animeResponse = MutableLiveData<AnimeResponse>()
        api.getAnimeList(query).enqueue(object : Callback<AnimeResponse> {
            override fun onResponse(call: Call<AnimeResponse>, response: Response<AnimeResponse>) {
                if (response.isSuccessful) {
                    animeResponse.value = response.body()
                } else {
                    animeResponse.value = null
                }
            }

            override fun onFailure(call: Call<AnimeResponse>, t: Throwable) {
                val errorAnime =
                    AnimeResponse.Anime("Http Error", "Http Error", "Http Error", "Http Error")
                val list = listOf<AnimeResponse.Anime>()
                if (BuildConfig.DEBUG && list.size != 0) {
                    error("Assertion failed")
                }
                val errorAnimeResponse = AnimeResponse(list)
                animeResponse.value = errorAnimeResponse
            }

        })
        return animeResponse
    }
}