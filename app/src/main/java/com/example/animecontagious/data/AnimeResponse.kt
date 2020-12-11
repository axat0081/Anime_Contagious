package com.example.animecontagious.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimeResponse(
    @SerializedName("top")
    val animeList: List<Anime>,
) : Parcelable {
    @Parcelize
    data class Anime(
        val title: String?,
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("start_date")
        val startDate: String?,
        val url: String?
    ) : Parcelable
}