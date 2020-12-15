package com.example.animecontagious.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.animecontagious.api.AnimeAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiringAnimeRepository @Inject constructor(private val animeAPI: AnimeAPI) {
   fun getAiringAnime(query : String)=
       Pager(
           config = PagingConfig(
               pageSize = 10,
               maxSize = 40,
               enablePlaceholders = false

           ),
           pagingSourceFactory = { AnimePagingSource(animeAPI,query,"Airing")}
       ).liveData
}