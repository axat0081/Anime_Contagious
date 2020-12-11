package com.example.animecontagious.data

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.animecontagious.BuildConfig

class AnimeViewModel @ViewModelInject constructor(
    private val repository: AnimeRepository
) : ViewModel() {
    companion object {
        const val DEFAULT_QUERY = "1"
    }

    private var currentQuery = MutableLiveData(DEFAULT_QUERY)
    val animeData = currentQuery.switchMap { queryString ->
        repository.getAnimeResults(queryString)
    }

    fun getAnimeResults(query: String) {
        currentQuery.value = query
    }
}