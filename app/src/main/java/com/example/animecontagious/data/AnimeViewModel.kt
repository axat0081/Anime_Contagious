package com.example.animecontagious.data

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn

class AnimeViewModel @ViewModelInject constructor(
    private val airingAnimeRepository: AiringAnimeRepository,
    private val upcomingRepository: AnimeUpcomingRepository
) : ViewModel() {
    companion object {
        const val DEFAULT_UPCOMING_QUERY = "1"
        const val DEFAULT_AIRING_QUERY = "1"
    }

    private var currentUpcomingQuery = MutableLiveData(DEFAULT_UPCOMING_QUERY)
    private var currentAiringQuery = MutableLiveData(DEFAULT_AIRING_QUERY)
    val animeUpcomingData = currentUpcomingQuery.switchMap { queryString ->
        upcomingRepository.getUpcomingAnime(queryString).cachedIn(viewModelScope)
    }
    val animeAiringData = currentAiringQuery.switchMap { queryString ->
        airingAnimeRepository.getAiringAnime(queryString).cachedIn(viewModelScope)
    }

    fun getUpcomingAnimeResults(query: String) {
        currentUpcomingQuery.value = query
    }

    fun getAiringAnimeResults(query: String) {
        currentAiringQuery.value = query
    }
}