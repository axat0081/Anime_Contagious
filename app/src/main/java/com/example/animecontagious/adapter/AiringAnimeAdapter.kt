package com.example.animecontagious.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import com.example.animecontagious.R
import com.example.animecontagious.data.AnimeResponse
import com.example.animecontagious.databinding.DisplayLayoutBinding

class AiringAnimeAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<AnimeResponse.Anime, AiringAnimeAdapter.AnimeViewHolder>(ANIME_COMPARATOR) {

    companion object {
        private val ANIME_COMPARATOR = object : DiffUtil.ItemCallback<AnimeResponse.Anime>() {
            override fun areItemsTheSame(
                oldItem: AnimeResponse.Anime,
                newItem: AnimeResponse.Anime
            ) = (oldItem.title == newItem.title)

            override fun areContentsTheSame(
                oldItem: AnimeResponse.Anime,
                newItem: AnimeResponse.Anime
            ) = oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding =
            DisplayLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    interface OnItemClickListener {
        fun onItemCLick(anime: AnimeResponse.Anime)
    }

    inner class AnimeViewHolder(private val binding: DisplayLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemCLick(item)
                    }
                }
            }
        }

        fun bind(anime: AnimeResponse.Anime) {
            binding.apply {
                if (anime.imageUrl == null) {
                    Glide.with(itemView)
                        .load(R.drawable.image_not_available)
                        .centerCrop()
                        .fitCenter()
                        .placeholder(R.drawable.image_not_available)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(Target.SIZE_ORIGINAL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                } else {
                    Glide.with(itemView)
                        .load(anime.imageUrl)
                        .placeholder(R.drawable.image_not_available)
                        .centerCrop()
                        .fitCenter()
                        .placeholder(R.drawable.image_not_available)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(Target.SIZE_ORIGINAL)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                }
                textView.text = anime.title
            }
        }
    }
}