package com.example.animecontagious.data

import androidx.paging.PagingSource
import com.example.animecontagious.api.AnimeAPI
import retrofit2.HttpException
import java.io.IOException

class AnimePagingSource(
    private val animeAPI: AnimeAPI,
    private val query: String,
    private val queryType: String
) : PagingSource<Int, AnimeResponse.Anime>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeResponse.Anime> {
        val position = params.key ?: 1
        return try {
            val response: AnimeResponse =
                if (queryType == "Upcoming") animeAPI.getUpcomingAnimeList(query)
                else animeAPI.getAiringAnimeList(query)
            val animeList = response.animeList
            LoadResult.Page(
                data = animeList,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (animeList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}