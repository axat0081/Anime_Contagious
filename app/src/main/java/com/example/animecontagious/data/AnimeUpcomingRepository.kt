package com.example.animecontagious.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.animecontagious.api.AnimeAPI
import javax.inject.Inject

class AnimeUpcomingRepository @Inject constructor(private val animeAPI: AnimeAPI) {
    fun getUpcomingAnime(query : String)=
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false

            ),
            pagingSourceFactory = { AnimePagingSource(animeAPI,query,"Upcoming")}
        ).liveData
}